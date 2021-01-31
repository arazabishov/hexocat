package com.abishov.hexocat.home.trending

import com.abishov.hexocat.components.RepositoryViewModel

sealed class TrendingViewState {
  object InProgress : TrendingViewState()
  class Success(val items: List<RepositoryViewModel>) : TrendingViewState()
  class Failure(val error: String?) : TrendingViewState()
}
