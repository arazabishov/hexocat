package com.abishov.hexocat.common.schedulers

import io.reactivex.schedulers.Schedulers

class TrampolineSchedulersProvider : SchedulerProvider {

  override fun computation() = Schedulers.trampoline()

  override fun io() = Schedulers.trampoline()

  override fun ui() = Schedulers.trampoline()
}
