package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides

@Module
internal class TrendingModule {

  @Provides
  @FragmentScope
  internal fun trendingPresenter(impl: TrendingPresenter): TrendingContract.Presenter = impl

  @Provides
  @FragmentScope
  internal fun trendingService(client: ApolloClient) = TrendingService(client)
}
