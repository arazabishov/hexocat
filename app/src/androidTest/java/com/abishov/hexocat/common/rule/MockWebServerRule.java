package com.abishov.hexocat.common.rule;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import com.abishov.hexocat.HexocatTestApp;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import io.appflate.restmock.RESTMockServer;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class MockWebServerRule implements TestRule {

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        HttpUrl baseUrl = HttpUrl.parse(RESTMockServer.getUrl());
        HexocatTestApp.overrideBaseUrl(baseUrl);

        RESTMockServer.reset();

        OkHttpClient client = HexocatTestApp.getInstance().appComponent().okHttpClient();
        IdlingResource okhttpIdlingResource = OkHttp3IdlingResource.create("okhttp", client);

        Espresso.registerIdlingResources(okhttpIdlingResource);
        try {
          base.evaluate();
        } finally {
          Espresso.unregisterIdlingResources(okhttpIdlingResource);
        }
      }
    };
  }
}
