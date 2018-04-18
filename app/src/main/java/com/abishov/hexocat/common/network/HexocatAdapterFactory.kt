package com.abishov.hexocat.common.network

import com.google.gson.TypeAdapterFactory
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory

@GsonTypeAdapterFactory
abstract class HexocatAdapterFactory : TypeAdapterFactory {

  companion object {
    fun create(): TypeAdapterFactory {
      return AutoValueGson_HexocatAdapterFactory()
    }
  }
}
