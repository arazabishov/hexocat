package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.models.Organization;
import com.abishov.hexocat.commons.models.Pager;
import com.abishov.hexocat.commons.models.Repository;

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
    private PublishSubject<Pager<Repository>> subject;

    // The pager with repositories returned by the service.
    private Pager<Repository> repositoryPager;

    // Corresponding RepositoryViewModels.
    private List<RepositoryViewModel> repositoryViewModels;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = PublishSubject.create();
        trendingRepository = new TrendingRepository(trendingService, queryDateProvider);

        Organization owner = Organization.create("test_login", "test_html_url");
        repositoryPager = Pager.create(Arrays.asList(
                Repository.create("test_repository_one",
                        "test_html_url_one", "test_description_one", owner),
                Repository.create("test_repository_two",
                        "test_html_url_two", "test_description_two", owner)));
        repositoryViewModels = Arrays.asList(
                RepositoryViewModel.create("test_repository_one"),
                RepositoryViewModel.create("test_repository_two"));

        when(trendingService.trendingRepositories("created:>2017-08-10")).thenReturn(subject);
        when(queryDateProvider.weekBeforeToday()).thenReturn("2017-08-10");
    }

    @Test
    public void trendingRepositoriesMustCallTrendingServiceWithCorrectFilter() {
        TestObserver<List<RepositoryViewModel>> testObserver =
                trendingRepository.trendingRepositories().test();

        subject.onNext(repositoryPager);
        subject.onComplete();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        assertThat(testObserver.values().get(0)).isEqualTo(repositoryViewModels);

        verify(queryDateProvider, times(1)).weekBeforeToday();
        verify(trendingService, times(1)).trendingRepositories("created:>2017-08-10");
    }
}
