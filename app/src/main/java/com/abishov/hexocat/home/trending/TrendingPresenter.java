package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.schedulers.SchedulerProvider;
import com.abishov.hexocat.commons.ui.Presenter;
import com.abishov.hexocat.commons.utils.OnErrorHandler;

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
                .observeOn(schedulerProvider.ui())
                .subscribe(view.renderRepositories(), OnErrorHandler.create()));
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }
}
