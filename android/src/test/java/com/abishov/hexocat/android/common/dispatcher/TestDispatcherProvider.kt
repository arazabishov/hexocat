package com.abishov.hexocat.android.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(dispatcher: CoroutineDispatcher) : DispatcherProvider {
    override val io = dispatcher
}
