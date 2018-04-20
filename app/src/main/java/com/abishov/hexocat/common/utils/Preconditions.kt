package com.abishov.hexocat.common.utils

object Preconditions {

  fun <T> isNull(`object`: T?, message: String): T {
    if (`object` == null) {
      throw IllegalArgumentException(message)
    }

    return `object`
  }
}
