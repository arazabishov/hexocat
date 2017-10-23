package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.github.Pager;
import com.abishov.hexocat.github.Repository;
import com.abishov.hexocat.github.User;
import com.abishov.hexocat.github.filters.Order;
import com.abishov.hexocat.github.filters.SearchQuery;
import com.abishov.hexocat.github.filters.Sort;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.bp.LocalDate;

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

    private TrendingRepository trendingRepository;

    // Used to simulate behaviour of the Observable.
    private PublishSubject<Pager<Repository>> subject;

    private Pager<Repository> repositoryPager;
    private SearchQuery searchQuery;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = PublishSubject.create();
        trendingRepository = new TrendingRepository(trendingService);

        User owner = User.create("test_login",
                "test_html_url", "test_avatar_url");
        repositoryPager = Pager.create(Arrays.asList(
                Repository.create("test_repository_one",
                        "test_html_url_one", 5, 10, "test_description_one", owner),
                Repository.create("test_repository_two",
                        "test_html_url_two", 4, 3, "test_description_two", owner)));

        searchQuery = new SearchQuery.Builder()
                .createdSince(LocalDate.parse("2017-08-10"))
                .build();

        when(trendingService.repositories(searchQuery, Sort.STARS, Order.DESC)).thenReturn(subject);
    }

    @Test
    public void trendingRepositoriesMustCallTrendingServiceWithCorrectFilter() {
        TestObserver<List<Repository>> testObserver =
                trendingRepository.trendingRepositories(searchQuery).test();

        subject.onNext(repositoryPager);
        subject.onComplete();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        assertThat(testObserver.values().get(0)).isEqualTo(repositoryPager.items());

        verify(trendingService, times(1))
                .repositories(searchQuery, Sort.STARS, Order.DESC);
    }
}
