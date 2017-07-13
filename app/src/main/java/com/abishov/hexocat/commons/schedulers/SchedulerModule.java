package com.abishov.hexocat.commons.schedulers;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class SchedulerModule {

    @NonNull
    private final SchedulerProvider provider;

    public SchedulerModule() {
        this.provider = new SchedulerProviderImpl();
    }

    public SchedulerModule(@NonNull SchedulerProvider provider) {
        this.provider = provider;
    }

    @NonNull
    @Provides
    @Singleton
    SchedulerProvider schedulerProvider() {
        return provider;
    }
}
