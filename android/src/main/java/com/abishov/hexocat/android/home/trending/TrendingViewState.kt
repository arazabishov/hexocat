package com.abishov.hexocat.android.home.trending

import com.abishov.hexocat.shared.models.RepositoryModel

sealed class TrendingViewState {
    object InProgress : TrendingViewState()
    class Success(val items: List<RepositoryModel>) : TrendingViewState()
    class Failure(val error: String?) : TrendingViewState()

    companion object {
        fun success(items: List<RepositoryModel>): TrendingViewState = Success(items)
        fun inProgress(): TrendingViewState = InProgress
        fun failure(error: String?): TrendingViewState = Failure(error)
    }
}
