package com.abishov.hexocat.home

import com.abishov.hexocat.common.dagger.FragmentScope
import com.abishov.hexocat.home.trending.TrendingFragment
import com.abishov.hexocat.home.trending.TrendingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeBindings {

  @FragmentScope
  @ContributesAndroidInjector(modules = arrayOf(TrendingModule::class))
  internal abstract fun contributesTrendingFragmentInjector(): TrendingFragment
}
