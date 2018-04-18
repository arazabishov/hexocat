package com.abishov.hexocat.common.utils

import android.util.Log
import hu.supercluster.paperwork.Paperwork
import timber.log.Timber
import java.util.*

class CrashReportingTree(private val paperwork: Paperwork) : Timber.Tree() {

  override fun log(priority: Int, tag: String, message: String, throwable: Throwable?) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
      return
    }

    if (priority == Log.ERROR && throwable != null) {
      Timber.tag(
        String.format(
          Locale.US, "Tag=[%s], sha=[%s], date=[%s]", tag,
          paperwork.get("gitSha"), paperwork.get("buildDate")
        )
      )

      Timber.e(throwable, message)
    }
  }
}
