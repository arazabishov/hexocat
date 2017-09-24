package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.models.repository.RepositoryApiModel;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

class TrendingRepository {
    private final TrendingService service;
    private final QueryDateProvider queryDateProvider;

    TrendingRepository(TrendingService service, QueryDateProvider queryDateProvider) {
        this.service = service;
        this.queryDateProvider = queryDateProvider;
    }


    Observable<List<RepositoryApiModel>> trendingRepositories() {
        String queryFilter = String.format(Locale.US, "created:>%s",
                queryDateProvider.weekBeforeToday());
        return service.trendingRepositories(queryFilter)
                .switchMap(pager -> Observable.fromIterable(pager.items())
                        .toList().toObservable());
    }
}
