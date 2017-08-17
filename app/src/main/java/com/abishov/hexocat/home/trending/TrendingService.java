package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.models.Pager;
import com.abishov.hexocat.commons.models.Repository;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TrendingService {

    @GET("search/repositories?sort=stars&order=desc")
    Observable<Pager<Repository>> trendingRepositories(@Query("q") String created);
}
