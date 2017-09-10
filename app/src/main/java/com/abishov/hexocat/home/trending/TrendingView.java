package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.views.View;

import javax.annotation.Nonnull;

import io.reactivex.functions.Consumer;

interface TrendingView extends View {

    @Nonnull
    Consumer<TrendingViewState> renderRepositories();
}
