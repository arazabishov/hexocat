package com.abishov.hexocat.common.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val OK_HTTP = "OkHttp"

@Module
@Singleton
class NetworkModule {

  @Provides
  @Singleton
  internal fun gsonAdapterFactory(): TypeAdapterFactory = HexocatAdapterFactory.create()

  @Provides
  @Singleton
  internal fun rxJavaAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

  @Provides
  @Singleton
  internal fun gson(adapterFactory: TypeAdapterFactory): Gson {
    return GsonBuilder()
      .registerTypeAdapterFactory(adapterFactory)
      .create()
  }

  @Provides
  @Singleton
  internal fun gsonFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

  @Provides
  @Singleton
  internal fun okHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, (10 * 1024 * 1024).toLong()) // 10 MiBs
  }

  @Provides
  @Singleton
  internal fun okHttpLogging(cache: Cache): Interceptor {
    return HttpLoggingInterceptor { message ->
      Timber.tag(OK_HTTP).d(message)
      Timber.tag(OK_HTTP).v(
        "Cache: requests=[%s], network=[%s], hits=[%s]",
        cache.requestCount(), cache.networkCount(), cache.hitCount()
      )
    }.setLevel(HttpLoggingInterceptor.Level.BASIC)
  }

  @Provides
  @Singleton
  internal fun okHttpClient(logger: Interceptor, cache: Cache): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(logger)
      .cache(cache)
      .connectTimeout(32, TimeUnit.SECONDS)
      .writeTimeout(32, TimeUnit.SECONDS)
      .readTimeout(32, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  internal fun retrofit(
    baseUrl: HttpUrl,
    factory: Converter.Factory,
    okHttpClient: OkHttpClient,
    rxJavaAdapterFactory: CallAdapter.Factory
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(factory)
      .addCallAdapterFactory(rxJavaAdapterFactory)
      .client(okHttpClient)
      .build()
  }
}