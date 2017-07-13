package com.abishov.hexocat;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;

@Module
@Singleton
public final class AppModule {
    private final Application app;

    public AppModule(@NonNull Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context context() {
        return app;
    }

    @Provides
    @Singleton
    Paperwork paperwork(@NonNull Context context) {
        return new Paperwork(context);
    }
}
