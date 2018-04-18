package com.abishov.hexocat.common.schedulers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

  @Provides
  @Singleton
  internal fun schedulerProvider() = object : SchedulerProvider {}
}
