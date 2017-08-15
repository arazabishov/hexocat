package com.abishov.hexocat.home.trending;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Observable;

final class TrendingRepository {

    @Nonnull
    private final TrendingService service;

    TrendingRepository(@Nonnull TrendingService service) {
        this.service = service;
    }

    @Nonnull
    Observable<List<RepositoryViewModel>> trendingRepositories() {
        return service.trendingRepositories()
                .switchMap(pager -> Observable.fromIterable(pager.items())
                        .map(repo -> RepositoryViewModel.create(repo.name()))
                        .toList().toObservable());
    }
}
