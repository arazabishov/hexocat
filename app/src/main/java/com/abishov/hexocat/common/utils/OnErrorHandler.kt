package com.abishov.hexocat.common.utils

import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.functions.Consumer

class OnErrorHandler private constructor() : Consumer<Throwable> {

  override fun accept(throwable: Throwable) {
    throw OnErrorNotImplementedException(throwable)
  }

  companion object {
    fun create(): Consumer<Throwable> {
      return OnErrorHandler()
    }
  }
}
