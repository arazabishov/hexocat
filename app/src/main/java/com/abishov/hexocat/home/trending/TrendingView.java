package com.abishov.hexocat.home.trending;

import com.abishov.hexocat.commons.ui.View;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.functions.Consumer;

interface TrendingView extends View {

    @Nonnull
    Consumer<List<RepositoryViewModel>> renderRepositories();
}
