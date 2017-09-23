package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;

import com.abishov.hexocat.models.repository.RepositoryApiModel;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import io.reactivex.Observable;

class TrendingRepository {

    @Nonnull
    private final TrendingService service;

    @NonNull
    private final QueryDateProvider queryDateProvider;

    TrendingRepository(@NonNull TrendingService service,
            @NonNull QueryDateProvider queryDateProvider) {
        this.service = service;
        this.queryDateProvider = queryDateProvider;
    }

    @Nonnull
    Observable<List<RepositoryApiModel>> trendingRepositories() {
        String queryFilter = String.format(Locale.US, "created:>%s",
                queryDateProvider.weekBeforeToday());
        return service.trendingRepositories(queryFilter)
                .switchMap(pager -> Observable.fromIterable(pager.items())
                        .toList().toObservable());
    }
}
