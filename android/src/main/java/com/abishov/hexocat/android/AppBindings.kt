package com.abishov.hexocat.android

import com.abishov.hexocat.android.common.dagger.ActivityScope
import com.abishov.hexocat.android.home.HomeActivity
import com.abishov.hexocat.android.home.HomeBindings
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeBindings::class])
    internal abstract fun contributeHomeActivityInjector(): HomeActivity
}
