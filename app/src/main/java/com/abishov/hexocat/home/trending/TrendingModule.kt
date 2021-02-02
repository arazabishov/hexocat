package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.common.dispatcher.DispatcherProvider
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides

@Module
internal class TrendingModule {

  @Provides
  @FragmentScope
  internal fun trendingService(client: ApolloClient) = TrendingService(client)

  @Provides
  @FragmentScope
  internal fun trendingViewModelFactory(
    dispatcherProvider: DispatcherProvider,
    service: TrendingService
  ) = TrendingViewModelFactory(dispatcherProvider, service)
}
