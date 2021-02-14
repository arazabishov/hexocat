package com.abishov.hexocat.android.common.rule

import androidx.test.espresso.IdlingRegistry
import com.abishov.hexocat.android.HexocatTestApp
import com.jakewharton.espresso.OkHttp3IdlingResource
import io.appflate.restmock.RESTMockServer
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {
                val baseUrl = RESTMockServer.getUrl().toHttpUrl()
                    .newBuilder()
                    .addPathSegment("graphql")
                    .build()

                HexocatTestApp.overrideBaseUrl(baseUrl)
                RESTMockServer.reset()

                val client = HexocatTestApp.instance.appComponent().okHttpClient()
                val okhttpIdlingResource = OkHttp3IdlingResource.create("okhttp", client)

                IdlingRegistry.getInstance().register(okhttpIdlingResource)
                try {
                    base.evaluate()
                } finally {
                    IdlingRegistry.getInstance().unregister(okhttpIdlingResource)
                }
            }
        }
    }
}
