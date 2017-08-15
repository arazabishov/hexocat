package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.models.Pager;
import com.abishov.hexocat.commons.models.Repository;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface TrendingService {

    @GET("search/repositories?sort=stars&order=desc&q=created:>2017-08-08")
    Observable<Pager<Repository>> trendingRepositories();
}
