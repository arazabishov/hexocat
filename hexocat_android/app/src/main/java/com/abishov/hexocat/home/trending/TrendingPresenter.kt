package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.common.schedulers.SchedulerProvider
import com.abishov.hexocat.common.utils.OnErrorHandler
import com.abishov.hexocat.composables.LanguageViewModel
import com.abishov.hexocat.composables.TopicViewModel
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.composables.RepositoryViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
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
    return trendingRepositoriesService.search(query, 32)
      .subscribeOn(schedulerProvider.io())
      .switchMap { trendingRepositories ->
        Observable.fromIterable(trendingRepositories)
          .map {
            val description = it.description ?: ""

            val forks = it.forkCount.toString()
            val stars = it.stargazers.totalCount.toString()
            val avatarUrl = it.owner.avatarUrl.toString().toHttpUrlOrNull()?.newBuilder()
              ?.addQueryParameter("s", "128")?.build()
              ?.toString() ?: ""

            val languages = it.languages?.edges?.mapNotNull { edge ->
              edge?.node?.let { node ->
                LanguageViewModel(node.name, node.color ?: "")
              }
            } ?: listOf()

            val topics = it.repositoryTopics.edges?.mapNotNull { edge ->
              edge?.node?.let { node ->
                TopicViewModel(node.topic.name)
              }
            } ?: listOf()

            RepositoryViewModel(
              it.name, description, forks, stars,
              avatarUrl,
              it.openGraphImageUrl.toString(),
              it.usesCustomOpenGraphImage,
              it.owner.login,
              it.url.toString(),
              languages,
              topics
            )
          }
          .toList()
          .toObservable()
      }
      .map { TrendingViewState.Success(it) as TrendingViewState }
      .startWith(TrendingViewState.InProgress)
      .onErrorReturn {
        val message = it.message ?: ""
        TrendingViewState.Failure(message)
      }
      .observeOn(schedulerProvider.ui())
  }
}
