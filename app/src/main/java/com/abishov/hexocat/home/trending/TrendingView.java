package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.views.View;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface TrendingView extends View {
    Observable<Object> retryActions();

    Consumer<TrendingViewState> renderRepositories();
}
