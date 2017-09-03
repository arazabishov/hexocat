package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.models.Organization;
import com.abishov.hexocat.commons.models.Repository;
import com.abishov.hexocat.commons.schedulers.TrampolineSchedulersProvider;
import com.abishov.hexocat.commons.views.ViewState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private TrendingView trendingView;

    @Mock
    private TrendingRepository trendingRepository;

    @Captor
    private ArgumentCaptor<ViewState<RepositoryViewModel>> repositoriesConsumer;

    // Faking behaviour of the trendingRepository.
    private BehaviorSubject<List<Repository>> listResults;

    // Some dummy data.
    private List<Repository> repositories;

    // ViewModels corresponding to dummy data.
    private List<RepositoryViewModel> repositoryViewModels;

    // Class under testing.
    private TrendingPresenter trendingPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        listResults = BehaviorSubject.create();
        trendingPresenter = new TrendingPresenter(
                new TrampolineSchedulersProvider(), trendingRepository);

        Organization owner = Organization.create("test_login", "test_html_url");
        repositories = Arrays.asList(
                Repository.create("test_repository_one",
                        "test_html_url_one", "test_description_one", owner),
                Repository.create("test_repository_two",
                        "test_html_url_two", "test_description_two", owner));
        repositoryViewModels = Arrays.asList(
                RepositoryViewModel.create("test_repository_one", "test_description_one"),
                RepositoryViewModel.create("test_repository_two", "test_description_two"));

        when(trendingRepository.trendingRepositories()).thenReturn(listResults);
    }

    @Test
    public void presenterMustPropagateCorrectStatesOnSuccess() throws Exception {
        trendingPresenter.onAttach(trendingView);
        assertThat(listResults.hasObservers()).isTrue();

        listResults.onNext(repositories);
        listResults.onComplete();

        verify(trendingView.renderRepositories(), times(2))
                .accept(repositoriesConsumer.capture());

        ViewState<RepositoryViewModel> viewStateProgress =
                repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        ViewState<RepositoryViewModel> viewStateSuccess =
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
        assertThat(listResults.hasObservers()).isTrue();

        listResults.onError(new Throwable("test_message"));
        listResults.onComplete();

        verify(trendingView.renderRepositories(), times(2))
                .accept(repositoriesConsumer.capture());

        ViewState<RepositoryViewModel> viewStateProgress =
                repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        ViewState<RepositoryViewModel> viewStateSuccess =
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
        assertThat(listResults.hasObservers()).isTrue();

        listResults.onNext(new ArrayList<>());
        listResults.onComplete();

        verify(trendingView.renderRepositories(), times(2))
                .accept(repositoriesConsumer.capture());

        ViewState<RepositoryViewModel> viewStateProgress =
                repositoriesConsumer.getAllValues().get(0);

        assertThat(viewStateProgress.isSuccess()).isFalse();
        assertThat(viewStateProgress.isInProgress()).isTrue();
        assertThat(viewStateProgress.isFailure()).isFalse();
        assertThat(viewStateProgress.isIdle()).isFalse();

        ViewState<RepositoryViewModel> viewStateSuccess =
                repositoriesConsumer.getAllValues().get(1);

        assertThat(viewStateSuccess.isSuccess()).isTrue();
        assertThat(viewStateSuccess.isInProgress()).isFalse();
        assertThat(viewStateSuccess.isFailure()).isFalse();
        assertThat(viewStateSuccess.isIdle()).isFalse();
        assertThat(viewStateSuccess.items()).isEmpty();
    }

    @Test
    public void presenterMustUnsubscribeFromViewOnDetach() {
        assertThat(listResults.hasObservers()).isFalse();

        trendingPresenter.onAttach(trendingView);
        assertThat(listResults.hasObservers()).isTrue();

        trendingPresenter.onDetach();
        assertThat(listResults.hasObservers()).isFalse();
    }
}
