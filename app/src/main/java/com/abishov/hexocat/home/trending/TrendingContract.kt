package com.abishov.hexocat.home.trending

import com.abishov.hexocat.github.filters.SearchQuery
import io.reactivex.Observable
import io.reactivex.functions.Consumer

internal interface TrendingContract {

  interface View : com.abishov.hexocat.common.views.View {

    fun searchQueries(): Observable<SearchQuery>

    fun bindTo(): Consumer<TrendingViewState>
  }

  interface Presenter : com.abishov.hexocat.common.views.Presenter<View>
}
