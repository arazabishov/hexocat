package com.abishov.hexocat.android.common.network

import com.abishov.hexocat.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_PAT}")
      .build()
    return chain.proceed(request)
  }
}
