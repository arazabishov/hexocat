package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.github.Pager;
import com.abishov.hexocat.github.Repository;
import com.abishov.hexocat.github.filters.Order;
import com.abishov.hexocat.github.filters.SearchQuery;
import com.abishov.hexocat.github.filters.Sort;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TrendingService {

  @GET("search/repositories")
  Observable<Pager<Repository>> repositories(
      @Query("q") SearchQuery query,
      @Query("sort") Sort sort,
      @Query("order") Order order);
}
