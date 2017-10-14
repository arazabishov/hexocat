package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.network.github.SearchQuery;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface TrendingContract {
    interface View extends com.abishov.hexocat.commons.views.View {
        Observable<SearchQuery> searchQueries();

        Consumer<TrendingViewState> bindTo();
    }

    interface Presenter extends com.abishov.hexocat.commons.views.Presenter<View> {
    }
}
