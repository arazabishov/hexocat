package com.abishov.hexocat.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object TestDispatcherProvider : DispatcherProvider {
  private var ioDispatcher: CoroutineDispatcher = Dispatchers.IO

  fun setIo(dispatcher: CoroutineDispatcher) {
    ioDispatcher = dispatcher
  }

  fun resetIo() {
    ioDispatcher = Dispatchers.IO
  }

  override val io = ioDispatcher
}
