package com.abishov.hexocat.android.home

import com.abishov.hexocat.android.common.dagger.FragmentScope
import com.abishov.hexocat.android.home.trending.TrendingFragment
import com.abishov.hexocat.android.home.trending.TrendingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeBindings {

  @FragmentScope
  @ContributesAndroidInjector(modules = [TrendingModule::class])
  internal abstract fun contributesTrendingFragmentInjector(): TrendingFragment
}
