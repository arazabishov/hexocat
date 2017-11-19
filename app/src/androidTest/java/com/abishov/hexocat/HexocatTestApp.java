package com.abishov.hexocat;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import com.abishov.hexocat.common.picasso.MockRequestHandler;
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

    AssetManager assetManager = InstrumentationRegistry.getContext().getAssets();
    return DaggerAppComponent.builder()
        .requestHandler(new MockRequestHandler(assetManager))
        .application(this)
        .baseUrl(baseUrl)
        .build();
  }

  public static void overrideBaseUrl(HttpUrl baseUrl) {
    getInstance().baseUrl = baseUrl;
    getInstance().setupAppComponent();
  }
}
