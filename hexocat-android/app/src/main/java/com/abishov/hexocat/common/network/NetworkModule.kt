package com.abishov.hexocat.common.network

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

private const val OK_HTTP = "OkHttp"

@Module
class NetworkModule {

  @Provides
  @Singleton
  internal fun jsonAdapterFactory(): JsonAdapter.Factory = KotlinJsonAdapterFactory();

  @Provides
  @Singleton
  internal fun rxJavaAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

  @Provides
  @Singleton
  internal fun moshi(adapterFactory: JsonAdapter.Factory): Moshi {
    return Moshi.Builder()
      .add(adapterFactory)
      .build()
  }

  @Provides
  @Singleton
  internal fun converterFactory(moshi: Moshi): Converter.Factory {
    return MoshiConverterFactory.create(moshi)
  }

  @Provides
  @Singleton
  internal fun okHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, (10 * 1024 * 1024).toLong()) // 10 MiBs
  }

  @Provides
  @Singleton
  internal fun okHttpLogging(cache: Cache): Interceptor {
    val interceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Timber.tag(OK_HTTP).d(message)
            Timber.tag(OK_HTTP).v(
                    "Cache: requests=[%s], network=[%s], hits=[%s]",
                    cache.requestCount(), cache.networkCount(), cache.hitCount()
            )
        }
    })

    return interceptor.apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }
  }

  @Provides
  @Singleton
  internal fun okHttpClient(
          logger: Interceptor,
          cache: Cache,
          sslSocketFactory: SSLSocketFactory?,
          trustManager: X509TrustManager?
  ): OkHttpClient {
    var builder = OkHttpClient.Builder()
      .addInterceptor(logger)
      .cache(cache)
      .connectTimeout(32, TimeUnit.SECONDS)
      .writeTimeout(32, TimeUnit.SECONDS)
      .readTimeout(32, TimeUnit.SECONDS)

      if (sslSocketFactory != null && trustManager != null) {
        builder = builder.sslSocketFactory(sslSocketFactory, trustManager)
      }

      return builder.build()
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