package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.network.github.SearchQuery;
import com.abishov.hexocat.commons.schedulers.SchedulerProvider;
import com.abishov.hexocat.commons.utils.OnErrorHandler;
import com.abishov.hexocat.home.repository.RepositoryViewModel;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

final class TrendingPresenter implements TrendingContract.Presenter {
    private final SchedulerProvider schedulerProvider;
    private final TrendingRepository trendingRepository;
    private final CompositeDisposable compositeDisposable;

    TrendingPresenter(SchedulerProvider schedulerProvider,
            TrendingRepository trendingRepository) {
        this.schedulerProvider = schedulerProvider;
        this.trendingRepository = trendingRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(TrendingContract.View view) {
        compositeDisposable.add(view.fetchRepositories()
                .switchMap(this::fetchRepositories)
                .subscribe(view.renderRepositories(), OnErrorHandler.create()));
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }

    private Observable<TrendingViewState> fetchRepositories(SearchQuery query) {
        return trendingRepository.trendingRepositories(query)
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
