package com.abishov.hexocat.android.home.trending

import com.abishov.hexocat.android.components.RepositoryViewModel

sealed class TrendingViewState {
    object InProgress : TrendingViewState()
    class Success(val items: List<RepositoryViewModel>) : TrendingViewState()
    class Failure(val error: String?) : TrendingViewState()

    companion object {
        fun success(items: List<RepositoryViewModel>): TrendingViewState = Success(items)
        fun inProgress(): TrendingViewState = InProgress
        fun failure(error: String?): TrendingViewState = Failure(error)
    }
}
