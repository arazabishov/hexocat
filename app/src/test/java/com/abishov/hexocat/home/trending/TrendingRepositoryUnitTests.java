package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.models.PagerApiModel;
import com.abishov.hexocat.models.organization.OrganizationApiModel;
import com.abishov.hexocat.models.repository.RepositoryApiModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public final class TrendingRepositoryUnitTests {

    @Mock
    private TrendingService trendingService;

    @Mock
    private QueryDateProvider queryDateProvider;

    private TrendingRepository trendingRepository;

    // Used to simulate behaviour of the Observable.
    private PublishSubject<PagerApiModel<RepositoryApiModel>> subject;

    // The pager with repositories returned by the service.
    private PagerApiModel<RepositoryApiModel> repositoryPager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = PublishSubject.create();
        trendingRepository = new TrendingRepository(trendingService, queryDateProvider);

        OrganizationApiModel owner = OrganizationApiModel.create("test_login",
                "test_html_url", "test_avatar_url");
        repositoryPager = PagerApiModel.create(Arrays.asList(
                RepositoryApiModel.create("test_repository_one",
                        "test_html_url_one", "test_description_one", owner),
                RepositoryApiModel.create("test_repository_two",
                        "test_html_url_two", "test_description_two", owner)));

        when(trendingService.trendingRepositories("created:>2017-08-10")).thenReturn(subject);
        when(queryDateProvider.weekBeforeToday()).thenReturn("2017-08-10");
    }

    @Test
    public void trendingRepositoriesMustCallTrendingServiceWithCorrectFilter() {
        TestObserver<List<RepositoryApiModel>> testObserver =
                trendingRepository.trendingRepositories().test();

        subject.onNext(repositoryPager);
        subject.onComplete();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        assertThat(testObserver.values().get(0)).isEqualTo(repositoryPager.items());

        verify(queryDateProvider, times(1)).weekBeforeToday();
        verify(trendingService, times(1)).trendingRepositories("created:>2017-08-10");
    }
}
