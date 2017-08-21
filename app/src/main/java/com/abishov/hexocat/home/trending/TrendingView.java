package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.views.View;
import com.abishov.hexocat.commons.views.ViewState;

import javax.annotation.Nonnull;

import io.reactivex.functions.Consumer;

interface TrendingView extends View {

    @Nonnull
    Consumer<ViewState<RepositoryViewModel>> renderRepositories();
}
