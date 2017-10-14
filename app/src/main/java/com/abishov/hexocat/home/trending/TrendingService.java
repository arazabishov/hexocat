package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.network.github.Order;
import com.abishov.hexocat.commons.network.github.SearchQuery;
import com.abishov.hexocat.commons.network.github.Sort;
import com.abishov.hexocat.models.PagerApiModel;
import com.abishov.hexocat.models.RepositoryApiModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TrendingService {

    @GET("search/repositories")
    Observable<PagerApiModel<RepositoryApiModel>> repositories(
            @Query("q") SearchQuery query,
            @Query("sort") Sort sort,
            @Query("order") Order order);
}
