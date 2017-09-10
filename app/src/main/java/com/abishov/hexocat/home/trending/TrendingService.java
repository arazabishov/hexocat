package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.models.PagerApiModel;
import com.abishov.hexocat.models.repository.RepositoryApiModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TrendingService {

    @GET("search/repositories?sort=stars&order=desc")
    Observable<PagerApiModel<RepositoryApiModel>> trendingRepositories(@Query("q") String created);
}
