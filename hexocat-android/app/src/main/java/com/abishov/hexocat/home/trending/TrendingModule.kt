package com.abishov.hexocat.home.trending

import com.abishov.hexocat.common.dagger.FragmentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class TrendingModule {

  @Provides
  @FragmentScope
  internal fun trendingPresenter(impl: TrendingPresenter): TrendingContract.Presenter = impl

  @Provides
  @FragmentScope
  internal fun trendingService(retrofit: Retrofit): TrendingService {
    return retrofit.create(TrendingService::class.java)
  }
}
