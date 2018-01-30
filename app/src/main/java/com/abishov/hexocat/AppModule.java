package com.abishov.hexocat;

import android.app.Application;
import android.content.Context;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;
import javax.inject.Singleton;

@Module
@Singleton
abstract class AppModule {

  @Provides
  @Singleton
  static Paperwork paperwork(Context context) {
    return new Paperwork(context);
  }

  @Binds
  @Singleton
  abstract Context context(Application app);
}
