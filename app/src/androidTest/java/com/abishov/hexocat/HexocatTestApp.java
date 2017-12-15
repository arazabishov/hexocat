package com.abishov.hexocat;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import com.abishov.hexocat.common.picasso.MockRequestHandler;
import okhttp3.HttpUrl;
import org.threeten.bp.Clock;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

public final class HexocatTestApp extends Hexocat {

  public static final String TEST_BASE_DATE = "2017-08-30T00:00:00+00:00";

  private HttpUrl baseUrl;

  public static HexocatTestApp getInstance() {
    Context appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    return (HexocatTestApp) appContext;
  }

  public static void overrideBaseUrl(HttpUrl baseUrl) {
    getInstance().baseUrl = baseUrl;
    getInstance().setupAppComponent();
  }

  @Override
  protected AppComponent prepareAppComponent() {
    if (baseUrl == null) {
      return super.prepareAppComponent();
    }

    AssetManager assetManager = InstrumentationRegistry.getContext().getAssets();
    return DaggerAppComponent.builder()
        .requestHandler(new MockRequestHandler(assetManager))
        .clock(createFixedClockInstance(TEST_BASE_DATE))
        .application(this)
        .baseUrl(baseUrl)
        .build();
  }

  private static Clock createFixedClockInstance(String dateTime) {
    Instant dateTimeInstant = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
        .atZone(ZoneOffset.UTC)
        .toInstant();
    return Clock.fixed(dateTimeInstant, ZoneOffset.UTC);
  }
}
