package com.abishov.hexocat

import com.abishov.hexocat.common.dagger.ActivityScope
import com.abishov.hexocat.home.HomeActivity
import com.abishov.hexocat.home.HomeBindings
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppBindings {

  @ActivityScope
  @ContributesAndroidInjector(modules = arrayOf(HomeBindings::class))
  internal abstract fun contributeHomeActivityInjector(): HomeActivity
}
