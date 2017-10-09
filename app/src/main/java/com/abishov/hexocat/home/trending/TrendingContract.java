package com.abishov.hexocat.home.trending;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface TrendingContract {
    interface View extends com.abishov.hexocat.commons.views.View {
        Observable<Object> retryActions();

        Consumer<TrendingViewState> renderRepositories();
    }

    interface Presenter extends com.abishov.hexocat.commons.views.Presenter<View> {
    }
}
