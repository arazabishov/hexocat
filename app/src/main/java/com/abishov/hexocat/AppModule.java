package com.abishov.hexocat;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;

@Singleton
@Module
final class AppModule {
    private final Application app;

    AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context context() {
        return app;
    }

    @Provides
    @Singleton
    Paperwork paperwork(Context context) {
        return new Paperwork(context);
    }
}
