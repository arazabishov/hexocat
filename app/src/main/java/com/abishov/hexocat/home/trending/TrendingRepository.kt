package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.github.Repository
import com.abishov.hexocat.github.filters.Order
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.github.filters.Sort
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
internal class TrendingRepository @Inject constructor(private val service: TrendingService) {

  fun trendingRepositories(query: SearchQuery): Observable<List<Repository>> {
    return service.repositories(query, Sort.STARS, Order.DESC)
      .switchMap { Observable.just(it.items()) }
  }
}
