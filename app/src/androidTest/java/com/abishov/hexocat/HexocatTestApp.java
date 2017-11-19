package com.abishov.hexocat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import okhttp3.HttpUrl;

public final class HexocatTestApp extends Hexocat {

  private HttpUrl baseUrl;

  public static HexocatTestApp getInstance() {
    Context appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    return (HexocatTestApp) appContext;
  }

  @Override
  protected AppComponent prepareAppComponent() {
    if (baseUrl == null) {
      return super.prepareAppComponent();
    }

    return DaggerAppComponent.builder()
        .baseUrl(baseUrl)
        .application(this)
        .build();
  }

  public void overrideBaseUrl(HttpUrl baseUrl) {
    this.baseUrl = baseUrl;
    setupAppComponent();
  }
}
