package com.abishov.hexocat

import android.app.Application
import com.abishov.hexocat.common.network.NetworkModule
import com.abishov.hexocat.common.picasso.PicassoModule
import com.abishov.hexocat.common.schedulers.SchedulerModule
import com.squareup.picasso.RequestHandler
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    NetworkModule::class,
    PicassoModule::class,
    SchedulerModule::class,
    AndroidInjectionModule::class,
    AppBindings::class
  ]
)
interface AppComponent {

  fun okHttpClient(): OkHttpClient

  fun inject(hexocat: Hexocat)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun clock(clock: Clock): Builder

    @BindsInstance
    fun baseUrl(baseUrl: HttpUrl): Builder

    @BindsInstance
    fun application(application: Application): Builder

    @BindsInstance
    fun requestHandler(requestHandler: RequestHandler?): Builder

    fun build(): AppComponent
  }
}
