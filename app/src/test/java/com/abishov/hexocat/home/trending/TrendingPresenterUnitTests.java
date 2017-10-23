package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.schedulers.TrampolineSchedulersProvider;
import com.abishov.hexocat.github.Repository;
import com.abishov.hexocat.github.User;
import com.abishov.hexocat.github.filters.SearchQuery;
import com.abishov.hexocat.home.repository.RepositoryViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TrendingPresenterUnitTests {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TrendingContract.View trendingView;

    @Mock
    private TrendingRepository trendingRepository;

    @Captor
    private ArgumentCaptor<TrendingViewState> repositoriesConsumer;

    // Faking behaviour of the TrendingRepository and TrendingContract.View.
    private BehaviorSubject<SearchQuery> viewQueries;
    private BehaviorSubject<List<Repository>> listResults;

    // Some dummy data.
    private List<Repository> repositories;

    // ViewModels corresponding to dummy data.
    private List<RepositoryViewModel> repositoryViewModels;

    private TrendingPresenter trendingPresenter;
    private SearchQuery searchQuery;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        viewQueries = BehaviorSubject.create();
        listResults = BehaviorSubject.create();
        trendingPresenter = new TrendingPresenter(
                new TrampolineSchedulersProvider(), trendingRepository);

        User owner = User.create("test_login", "test_html_url", "test_avatar_url");
        repositories = Arrays.asList(
                Repository.create("test_repository_one",
                        "test_html_url_one", 5, 10, "test_description_one", owner),
                Repository.create("test_repository_two",
                        "test_html_url_two", 7, 11, "test_description_two", owner));
        repositoryViewModels = Arrays.asList(
                RepositoryViewModel.create("test_repository_one", "test_description_one",
                        "5", "10", "test_avatar_url", "test_login"),
                RepositoryViewModel.create("test_repository_two", "test_description_two",
                        "7", "11", "test_avatar_url", "test_login"));

        searchQuery = new SearchQuery.Builder()
                .createdSince(LocalDate.parse("2017-08-10"))
                .build();

        when(trendingView.searchQueries()).thenReturn(viewQueries);
        when(trendingRepository.trendingRepositories(searchQuery)).thenReturn(listResults);
    }

    @Test
    public void presenterMustPropagateCorrectStatesOnSuccess() throws Exception {
        trendingPresenter.onAttach(trendingView);
        assertThat(viewQueries.hasObservers()).isTrue();

        viewQueries.onNext(searchQuery);
        listResults.onNext(repositories);
        listResults.onComplete();

        verify(trendingView.bindTo(), times(2))
                .accept(repositoriesConsumer.capture());

        TrendingViewState viewStateProgress =
                repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        TrendingViewState viewStateSuccess =
                repositoriesConsumer.getAllValues().get(1);

        assertThat(viewStateSuccess.isSuccess()).isTrue();
        assertThat(viewStateSuccess.isInProgress()).isFalse();
        assertThat(viewStateSuccess.isFailure()).isFalse();
        assertThat(viewStateSuccess.isIdle()).isFalse();
        assertThat(viewStateSuccess.items()).isEqualTo(repositoryViewModels);
    }

    @Test
    public void presenterMustPropagateCorrectStatesOnFailure() throws Exception {
        trendingPresenter.onAttach(trendingView);
        assertThat(viewQueries.hasObservers()).isTrue();

        viewQueries.onNext(searchQuery);
        listResults.onError(new Throwable("test_message"));
        listResults.onComplete();

        verify(trendingView.bindTo(), times(2))
                .accept(repositoriesConsumer.capture());

        TrendingViewState viewStateProgress =
                repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        TrendingViewState viewStateSuccess =
                repositoriesConsumer.getAllValues().get(1);

        assertThat(viewStateSuccess.isSuccess()).isFalse();
        assertThat(viewStateSuccess.isInProgress()).isFalse();
        assertThat(viewStateSuccess.isFailure()).isTrue();
        assertThat(viewStateSuccess.isIdle()).isFalse();
        assertThat(viewStateSuccess.items()).isEmpty();
    }

    @Test
    public void presenterMustPropagateCorrectStatesWhenNoItems() throws Exception {
        trendingPresenter.onAttach(trendingView);
        assertThat(viewQueries.hasObservers()).isTrue();

        viewQueries.onNext(searchQuery);
        listResults.onNext(new ArrayList<>());
        listResults.onComplete();

        verify(trendingView.bindTo(), times(2))
                .accept(repositoriesConsumer.capture());

        TrendingViewState viewStateProgress = repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        TrendingViewState viewStateSuccess = repositoriesConsumer.getAllValues().get(1);

        assertThat(viewStateSuccess.isSuccess()).isTrue();
        assertThat(viewStateSuccess.isInProgress()).isFalse();
        assertThat(viewStateSuccess.isFailure()).isFalse();
        assertThat(viewStateSuccess.isIdle()).isFalse();
        assertThat(viewStateSuccess.items()).isEmpty();
    }

    @Test
    public void presenterMustUnsubscribeFromViewOnDetach() {
        assertThat(listResults.hasObservers()).isFalse();
        assertThat(viewQueries.hasObservers()).isFalse();

        trendingPresenter.onAttach(trendingView);
        assertThat(listResults.hasObservers()).isFalse();
        assertThat(viewQueries.hasObservers()).isTrue();

        trendingPresenter.onDetach();
        assertThat(listResults.hasObservers()).isFalse();
        assertThat(viewQueries.hasObservers()).isFalse();
    }
}
