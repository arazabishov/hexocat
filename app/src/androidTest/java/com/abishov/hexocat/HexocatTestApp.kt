package com.abishov.hexocat

import androidx.test.platform.app.InstrumentationRegistry
import com.abishov.hexocat.common.picasso.MockRequestHandler
import io.appflate.restmock.RESTMockServer
import okhttp3.HttpUrl
import org.threeten.bp.Clock
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

const val TEST_BASE_DATE = "2017-08-30T00:00:00+00:00"

class HexocatTestApp : Hexocat() {

  private var baseUrl: HttpUrl? = null

  override fun prepareAppComponent(): AppComponent {
    if (baseUrl == null) {
      return super.prepareAppComponent()
    }

    val assetManager = InstrumentationRegistry.getInstrumentation().context.assets
    return DaggerAppComponent.builder()
      .requestHandler(MockRequestHandler(assetManager))
      .sslSocketFactory(RESTMockServer.getSSLSocketFactory())
      .trustManager(RESTMockServer.getTrustManager())
      .clock(createFixedClockInstance(TEST_BASE_DATE))
      .application(this)
      .baseUrl(baseUrl!!)
      .build()
  }

  companion object {
    val instance: HexocatTestApp
      get() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        return instrumentation.targetContext.applicationContext as HexocatTestApp
      }

    fun overrideBaseUrl(baseUrl: HttpUrl) {
      instance.baseUrl = baseUrl
      instance.setupAppComponent()
    }

    private fun createFixedClockInstance(dateTime: String): Clock {
      val dateTimeInstant = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
        .atZone(ZoneOffset.UTC)
        .toInstant()
      return Clock.fixed(dateTimeInstant, ZoneOffset.UTC)
    }
  }
}
