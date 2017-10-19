package com.abishov.hexocat;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;

@Module
@Singleton
abstract class AppModule {

    @Binds
    @Singleton
    abstract Context context(Application app);

    @Provides
    @Singleton
    static Paperwork paperwork(Context context) {
        return new Paperwork(context);
    }
}
