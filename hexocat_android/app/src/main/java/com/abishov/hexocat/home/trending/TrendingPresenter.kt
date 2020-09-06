package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.common.schedulers.SchedulerProvider
import com.abishov.hexocat.common.utils.OnErrorHandler
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.home.repository.RepositoryViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@FragmentScope
internal class TrendingPresenter @Inject constructor(
  private val schedulerProvider: SchedulerProvider,
  private val trendingRepositoriesService: TrendingService
) : TrendingContract.Presenter {
  private val compositeDisposable = CompositeDisposable()

  override fun onAttach(view: TrendingContract.View) {
    compositeDisposable.add(view.searchQueries()
      .switchMap { fetchRepositories(it) }
      .subscribe(view.bindTo(), OnErrorHandler.create()))
  }

  override fun onDetach() {
    compositeDisposable.clear()
  }

  private fun fetchRepositories(query: SearchQuery): Observable<TrendingViewState> {
    return trendingRepositoriesService.search(query, 64)
      .subscribeOn(schedulerProvider.io())
      .switchMap { trendingRepositories ->
        Observable.fromIterable(trendingRepositories)
          .map {
            val description = it.description ?: ""

            val forks = it.forkCount.toString()
            val stars = it.stargazers.totalCount.toString()

            RepositoryViewModel(
              it.name, description, forks, stars,
              it.owner.avatarUrl.toString(),
              it.owner.login,
              it.url.toString()
            )
          }
          .toList()
          .toObservable()
      }
      .map { TrendingViewState.Success(it) as TrendingViewState }
      .startWith(TrendingViewState.InProgress())
      .onErrorReturn {
        val message = it.message ?: ""
        TrendingViewState.Failure(message)
      }
      .observeOn(schedulerProvider.ui())
  }
}
