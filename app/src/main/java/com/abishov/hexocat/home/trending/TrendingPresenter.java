package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abishov.hexocat.commons.schedulers.SchedulerProvider;
import com.abishov.hexocat.commons.utils.OnErrorHandler;
import com.abishov.hexocat.commons.views.Presenter;
import com.abishov.hexocat.home.repository.RepositoryViewModel;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

final class TrendingPresenter implements Presenter<TrendingView, TrendingViewState> {

    @NonNull
    private final SchedulerProvider schedulerProvider;

    @NonNull
    private final TrendingRepository trendingRepository;

    @NonNull
    private final CompositeDisposable compositeDisposable;

    TrendingPresenter(@NonNull SchedulerProvider schedulerProvider,
            @NonNull TrendingRepository trendingRepository) {
        this.schedulerProvider = schedulerProvider;
        this.trendingRepository = trendingRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(@NonNull TrendingView view, @Nullable TrendingViewState viewState) {
        Observable<TrendingViewState> viewStateObservable = viewState == null ?
                fetchRepositories() : Observable.just(viewState);

        compositeDisposable.add(viewStateObservable
                .subscribe(view.renderRepositories(), OnErrorHandler.create()));

        compositeDisposable.add(view.retryActions()
                .switchMap(event -> fetchRepositories())
                .subscribe(view.renderRepositories(), OnErrorHandler.create()));
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }

    private Observable<TrendingViewState> fetchRepositories() {
        return trendingRepository.trendingRepositories()
                .subscribeOn(schedulerProvider.io())
                .switchMap(repositories -> Observable.fromIterable(repositories)
                        .map(repo -> {
                            String description = repo.description() == null ? "" : repo.description();
                            String forks = String.valueOf(repo.forks());
                            String stars = String.valueOf(repo.stars());
                            return RepositoryViewModel.create(repo.name(), description,
                                    forks, stars, repo.owner().avatarUrl(), repo.owner().login());
                        })
                        .toList().toObservable())
                .map(TrendingViewState::success)
                .startWith(TrendingViewState.progress())
                .onErrorReturn(TrendingViewState::failure)
                .observeOn(schedulerProvider.ui());
    }
}
