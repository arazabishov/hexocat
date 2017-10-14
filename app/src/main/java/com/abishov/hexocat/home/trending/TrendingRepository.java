package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.github.filters.Order;
import com.abishov.hexocat.github.filters.SearchQuery;
import com.abishov.hexocat.github.filters.Sort;
import com.abishov.hexocat.github.RepositoryApiModel;

import java.util.List;

import io.reactivex.Observable;

class TrendingRepository {
    private final TrendingService service;

    TrendingRepository(TrendingService service) {
        this.service = service;
    }

    Observable<List<RepositoryApiModel>> trendingRepositories(SearchQuery query) {
        return service.repositories(query, Sort.STARS, Order.DESC)
                .switchMap(pager -> Observable.just(pager.items()));
    }
}
