package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.models.PagerApiModel;
import com.abishov.hexocat.models.OrganizationApiModel;
import com.abishov.hexocat.models.RepositoryApiModel;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import io.reactivex.subjects.PublishSubject;

@RunWith(JUnit4.class)
public final class TrendingRepositoryUnitTests {

    @Mock
    private TrendingService trendingService;

    private TrendingRepository trendingRepository;

    // Used to simulate behaviour of the Observable.
    private PublishSubject<PagerApiModel<RepositoryApiModel>> subject;

    // The pager with repositories returned by the service.
    private PagerApiModel<RepositoryApiModel> repositoryPager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = PublishSubject.create();
        trendingRepository = new TrendingRepository(trendingService);

        OrganizationApiModel owner = OrganizationApiModel.create("test_login",
                "test_html_url", "test_avatar_url");
        repositoryPager = PagerApiModel.create(Arrays.asList(
                RepositoryApiModel.create("test_repository_one",
                        "test_html_url_one", 5, 10, "test_description_one", owner),
                RepositoryApiModel.create("test_repository_two",
                        "test_html_url_two", 4, 3, "test_description_two", owner)));

//        when(trendingService.repositories("created:>2017-08-10")).thenReturn(subject);
//        when(queryDateProvider.dateBefore(7)).thenReturn("2017-08-10");
    }
//
//    @Test
//    public void trendingRepositoriesMustCallTrendingServiceWithCorrectFilter() {
//        TestObserver<List<RepositoryApiModel>> testObserver =
//                trendingRepository.trendingRepositories(7).test();
//
//        subject.onNext(repositoryPager);
//        subject.onComplete();
//
//        testObserver.assertNoErrors();
//        testObserver.assertComplete();
//        testObserver.assertValueCount(1);
//
//        assertThat(testObserver.values().get(0)).isEqualTo(repositoryPager.items());
//
//        verify(queryDateProvider, times(1)).dateBefore(7);
//        verify(trendingService, times(1)).repositories("created:>2017-08-10");
//    }
}
