package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.network.github.Order;
import com.abishov.hexocat.commons.network.github.SearchQuery;
import com.abishov.hexocat.commons.network.github.Sort;
import com.abishov.hexocat.models.repository.RepositoryApiModel;

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
