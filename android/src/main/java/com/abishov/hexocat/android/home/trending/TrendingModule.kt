package com.abishov.hexocat.android.home.trending

import com.abishov.hexocat.android.common.dagger.FragmentScope
import com.abishov.hexocat.android.common.dispatcher.DispatcherProvider
import com.abishov.hexocat.shared.GithubService
import com.abishov.hexocat.shared.GithubServiceFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

@Module
internal class TrendingModule {

    @Provides
    @FragmentScope
    internal fun githubServiceFactory(
        serverUrl: HttpUrl,
        okHttpClient: OkHttpClient
    ) = GithubServiceFactory(serverUrl, okHttpClient)

    @Provides
    @FragmentScope
    internal fun githubService(factory: GithubServiceFactory) = factory.create()

    @Provides
    @FragmentScope
    internal fun trendingViewModelFactory(
        dispatcherProvider: DispatcherProvider,
        service: GithubService
    ) = TrendingViewModelFactory(dispatcherProvider, service)
}
