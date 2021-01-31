package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.common.schedulers.SchedulerProvider
import com.abishov.hexocat.common.utils.OnErrorHandler
import com.abishov.hexocat.components.*
import com.abishov.hexocat.github.filters.SearchQuery
import com.github.TrendingRepositoriesQuery
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private fun TrendingRepositoriesQuery.AsRepository.toViewModel(): RepositoryViewModel {
  val primaryLanguage = primaryLanguage?.let {
    LanguageViewModel(it.name, it.color)
  }

  val topics = repositoryTopics.topics?.mapNotNull { node ->
    node?.let { TopicViewModel(it.topic.name) }
  }

  val contributors = mentionableUsers.contributors?.mapNotNull { node ->
    node?.let { ContributorViewModel(node.id, node.avatarUrl) }
  }

  val users = contributors?.let {
    MentionableUsersViewModel(it, mentionableUsers.totalCount)
  }

  val owner = OwnerViewModel(
    id = owner.id,
    login = owner.login,
    avatarUrl = owner.avatarUrl
  )

  val bannerUrl = if (usesCustomOpenGraphImage) openGraphImageUrl else null

  return RepositoryViewModel(
    name = name,
    description = description,
    bannerUrl = bannerUrl,
    stars = stargazerCount,
    url = url,
    owner = owner,
    primaryLanguage = primaryLanguage,
    topics = topics,
    mentionableUsers = users,
  )
}

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
      .map { repositories -> repositories.map { it.toViewModel() } }
      .map { TrendingViewState.Success(it) as TrendingViewState }
      .startWith(TrendingViewState.InProgress)
      .onErrorReturn { TrendingViewState.Failure(it.message) }
      .observeOn(schedulerProvider.ui())
  }
}
