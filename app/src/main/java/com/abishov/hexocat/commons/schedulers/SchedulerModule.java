package com.abishov.hexocat.commons.schedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public final class SchedulerModule {
    private final SchedulerProvider provider;

    public SchedulerModule() {
        this.provider = new SchedulerProviderImpl();
    }

    public SchedulerModule(SchedulerProvider provider) {
        this.provider = provider;
    }

    @Provides
    @Singleton
    SchedulerProvider schedulerProvider() {
        return provider;
    }
}
