package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.schedulers.SchedulerProvider;
import com.abishov.hexocat.commons.utils.OnErrorHandler;
import com.abishov.hexocat.commons.views.Presenter;
import com.abishov.hexocat.commons.views.ViewState;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

final class TrendingPresenter implements Presenter<TrendingView> {

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
    public void onAttach(@NonNull TrendingView view) {
        compositeDisposable.add(trendingRepository.trendingRepositories()
                .subscribeOn(schedulerProvider.io())
                .switchMap(repositories -> Observable.fromIterable(repositories)
                        .map(repo -> RepositoryViewModel.create(repo.name(),
                                repo.description() == null ? "-" : repo.description()))
                        .toList().toObservable())
                .map(ViewState::success)
                .startWith(ViewState.progress())
                .onErrorReturn(ViewState::failure)
                .observeOn(schedulerProvider.ui())
                .subscribe(view.renderRepositories(), OnErrorHandler.create()));
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }
}
