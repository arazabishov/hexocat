package com.abishov.hexocat.android.common.network

import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

private const val OK_HTTP = "OkHttp"

class HttpLogger(private val cache: Cache) : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Timber.tag(OK_HTTP).d(message)
    Timber.tag(OK_HTTP).v(
      "Cache: requests=[%s], network=[%s], hits=[%s]",
      cache.requestCount(), cache.networkCount(), cache.hitCount()
    )
  }
}
