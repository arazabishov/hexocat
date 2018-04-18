package com.abishov.hexocat.home.trending

import com.abishov.hexocat.home.repository.RepositoryViewModel

sealed class TrendingViewState {
  class Idle : TrendingViewState()
  class InProgress : TrendingViewState()
  class Success(val items: List<RepositoryViewModel>): TrendingViewState()
  class Failure(val error: String): TrendingViewState()
}