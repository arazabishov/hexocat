package com.abishov.hexocat.common.network

import android.content.Context
import android.net.Uri
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomTypeAdapter
import com.github.type.CustomType
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun okHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, (10 * 1024 * 1024).toLong()) // 10 MiBs
  }

  @Provides
  @Singleton
  fun httpLogger(cache: Cache): HttpLoggingInterceptor.Logger = HttpLogger(cache)

  @IntoSet
  @Provides
  @Singleton
  fun authenticationInterceptor(): Interceptor = AuthInterceptor()

  @IntoSet
  @Provides
  @Singleton
  fun okHttpLogging(logger: HttpLoggingInterceptor.Logger): Interceptor {
    val interceptor = HttpLoggingInterceptor(logger)

    return interceptor.apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  @Provides
  @Singleton
  fun okHttpClient(
    cache: Cache,
    interceptors: Set<@JvmSuppressWildcards Interceptor>,
    sslSocketFactory: SSLSocketFactory?,
    trustManager: X509TrustManager?
  ): OkHttpClient {
    val builder = OkHttpClient.Builder()
      .cache(cache)
      .connectTimeout(32, TimeUnit.SECONDS)
      .writeTimeout(32, TimeUnit.SECONDS)
      .readTimeout(32, TimeUnit.SECONDS)

    interceptors.forEach { builder.addInterceptor(it) }

    if (sslSocketFactory != null && trustManager != null) {
      builder.sslSocketFactory(sslSocketFactory, trustManager)
    }

    return builder.build()
  }

  @Provides
  @Singleton
  fun uriTypeAdapter(): CustomTypeAdapter<Uri> = UriTypeAdapter()

  @Provides
  @Singleton
  fun apolloClient(
    baseUrl: HttpUrl,
    client: OkHttpClient,
    uriTypeAdapter: CustomTypeAdapter<Uri>
  ): ApolloClient {
    return ApolloClient.builder()
      .addCustomTypeAdapter(CustomType.URI, uriTypeAdapter)
      .okHttpClient(client)
      .serverUrl(baseUrl)
      .build()
  }
}
