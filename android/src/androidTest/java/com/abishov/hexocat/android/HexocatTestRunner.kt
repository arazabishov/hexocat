package com.abishov.hexocat.android

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import io.appflate.restmock.RESTMockOptions
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger

class HexocatTestRunner : AndroidJUnitRunner() {

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
}
