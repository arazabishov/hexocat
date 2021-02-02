package com.abishov.hexocat.home.trending

import androidx.lifecycle.*
import com.abishov.hexocat.common.dispatcher.DispatcherProvider
import com.abishov.hexocat.components.*
import com.abishov.hexocat.github.filters.SearchQuery
import com.github.TrendingRepositoriesQuery
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

class TrendingViewModelFactory(
  private val dispatcherProvider: DispatcherProvider,
  private val service: TrendingService
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return modelClass.getConstructor(DispatcherProvider::class.java, TrendingService::class.java)
      .newInstance(dispatcherProvider, service)
  }
}

class TrendingViewModel(
  private val dispatcherProvider: DispatcherProvider,
  private val service: TrendingService
) : ViewModel() {
  private val _screenState = MutableLiveData<TrendingViewState>()

  val screenState: LiveData<TrendingViewState>
    get() = _screenState

  fun fetchRepositories(searchQuery: SearchQuery) {
    viewModelScope.launch {
      service.search(searchQuery, 32)
        .flowOn(dispatcherProvider.io)
        .map { repositories -> repositories.map { it.toViewModel() } }
        .map { TrendingViewState.success(it) }
        .onStart { emit(TrendingViewState.inProgress()) }
        .catch { emit(TrendingViewState.failure(it.message)) }
        .onEach {
          _screenState.value = it
        }.launchIn(this)
    }
  }
}
