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
  private val trendingRepository: TrendingRepository
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
    return trendingRepository.trendingRepositories(query)
      .subscribeOn(schedulerProvider.io())
      .switchMap {
        Observable.fromIterable(it)
          .map {
            val description = if (it.description() == null) "" else it.description()
            val forks = it.forks().toString()
            val stars = it.stars().toString()
            RepositoryViewModel.create(
              it.name(), description, forks, stars,
              it.owner().avatarUrl(),
              it.owner().login(),
              it.htmlUrl()
            )
          }
          .toList()
          .toObservable()
      }
      .map { TrendingViewState.success(it) }
      .startWith(TrendingViewState.progress())
      .onErrorReturn { TrendingViewState.failure(it) }
      .observeOn(schedulerProvider.ui())
  }
}
