package com.abishov.hexocat

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import hu.supercluster.paperwork.Paperwork
import javax.inject.Singleton

@Module
internal class AppModule {

  @Provides
  @Singleton
  internal fun context(app: Application): Context = app

  @Provides
  @Singleton
  internal fun paperwork(context: Context) = Paperwork(context)
}
