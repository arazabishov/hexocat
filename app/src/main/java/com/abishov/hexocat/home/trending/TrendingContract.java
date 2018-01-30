package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.github.filters.SearchQuery;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface TrendingContract {

  interface View extends com.abishov.hexocat.common.views.View {

    Observable<SearchQuery> searchQueries();

    Consumer<TrendingViewState> bindTo();
  }

  interface Presenter extends com.abishov.hexocat.common.views.Presenter<View> {

  }
}
