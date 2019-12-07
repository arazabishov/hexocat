package com.abishov.hexocat

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.appflate.restmock.RESTMockOptions
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.reactivex.plugins.RxJavaPlugins

class HexocatTestRunner : AndroidJUnitRunner() {

  @Throws(
    InstantiationException::class,
    IllegalAccessException::class,
    ClassNotFoundException::class
  )
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return Instrumentation.newApplication(HexocatTestApp::class.java, context)
  }

  override fun onCreate(arguments: Bundle) {
    super.onCreate(arguments)

    val parser = AndroidAssetsFileParser(context)
    val options = RESTMockOptions.Builder()
            .useHttps(true)
            .build()

    RESTMockServerStarter.startSync(parser, AndroidLogger(), options)
  }

  override fun onStart() {
    RxJavaPlugins.setInitComputationSchedulerHandler(
      Rx2Idler.create("rxjava-scheduler-computation")
    )

    RxJavaPlugins.setInitIoSchedulerHandler(
      Rx2Idler.create("rxjava-scheduler-io")
    )

    super.onStart()
  }
}
