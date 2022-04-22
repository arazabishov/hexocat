package com.abishov.hexocat.android.home.trending

import androidx.lifecycle.*
import com.abishov.hexocat.android.common.dispatcher.DispatcherProvider
import com.abishov.hexocat.shared.GithubService
import com.abishov.hexocat.shared.filters.SearchQuery
import kotlinx.coroutines.launch

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
                _screenState.postValue(TrendingViewState.success(repositories))
            } catch (throwable: Throwable) {
                _screenState.postValue(TrendingViewState.failure(throwable.message))
            }
        }
    }
}
