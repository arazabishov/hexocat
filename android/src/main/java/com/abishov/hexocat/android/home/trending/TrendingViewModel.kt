package com.abishov.hexocat.android.home.trending

import android.net.Uri
import androidx.lifecycle.*
import com.abishov.hexocat.android.common.dispatcher.DispatcherProvider
import com.abishov.hexocat.android.components.*
import com.abishov.hexocat.shared.GithubService
import com.abishov.hexocat.shared.filters.SearchQuery
import com.github.SearchRepositoriesQuery
import kotlinx.coroutines.launch

private fun SearchRepositoriesQuery.AsRepository.toViewModel(): RepositoryViewModel {
    val primaryLanguage = primaryLanguage?.let {
        LanguageViewModel(it.name, it.color)
    }

    val topics = repositoryTopics.topics?.mapNotNull { node ->
        node?.let { TopicViewModel(it.topic.name) }
    }

    val contributors = mentionableUsers.contributors?.mapNotNull { node ->
        node?.let { ContributorViewModel(node.id, Uri.parse(node.avatarUrl.toString())) }
    }

    val users = contributors?.let {
        MentionableUsersViewModel(it, mentionableUsers.totalCount)
    }

    val owner = OwnerViewModel(
        id = owner.id,
        login = owner.login,
        avatarUrl = Uri.parse(owner.avatarUrl.toString())
    )

    val bannerUrl = if (usesCustomOpenGraphImage) Uri.parse(openGraphImageUrl.toString()) else null

    return RepositoryViewModel(
        name = name,
        description = description,
        bannerUrl = bannerUrl,
        stars = stargazerCount,
        url = Uri.parse(url.toString()),
        owner = owner,
        primaryLanguage = primaryLanguage,
        topics = topics,
        mentionableUsers = users,
    )
}

class TrendingViewModelFactory(
    private val dispatcherProvider: DispatcherProvider,
    private val service: GithubService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            DispatcherProvider::class.java,
            GithubService::class.java
        ).newInstance(dispatcherProvider, service)
    }
}

class TrendingViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val service: GithubService
) : ViewModel() {
    private val _screenState = MutableLiveData<TrendingViewState>()

    val screenState: LiveData<TrendingViewState>
        get() = _screenState

    fun fetchRepositories(searchQuery: SearchQuery) {
        viewModelScope.launch(dispatcherProvider.io) {
            _screenState.postValue(TrendingViewState.inProgress())

            try {
                val repositories = service.search(searchQuery, 32)
                    .map { it.toViewModel() }
                _screenState.postValue(TrendingViewState.success(repositories))
            } catch (throwable: Throwable) {
                _screenState.postValue(TrendingViewState.failure(throwable.message))
            }
        }
    }
}
