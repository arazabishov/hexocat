package com.abishov.hexocat

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.KotlinJsonAdapterFactory

object Inject {

  fun moshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }
}
