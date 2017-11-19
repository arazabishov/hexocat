package com.abishov.hexocat.common.schedulers;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

@Module
public abstract class SchedulerModule {

  @Binds
  @Singleton
  abstract SchedulerProvider schedulerProvider(SchedulerProviderImpl impl);
}
