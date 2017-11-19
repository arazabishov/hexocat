package com.abishov.hexocat;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.runner.AndroidJUnitRunner;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.squareup.rx2.idler.Rx2Idler;
import io.appflate.restmock.RESTMockFileParser;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import io.appflate.restmock.android.AndroidLogger;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public final class HexocatTestRunner extends AndroidJUnitRunner {

  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return Instrumentation.newApplication(HexocatTestApp.class, context);
  }

  @Override
  public void onCreate(Bundle arguments) {
    super.onCreate(arguments);

    RESTMockFileParser parser = new AndroidAssetsFileParser(getContext());
    RESTMockServerStarter.startSync(parser, new AndroidLogger());
    HexocatTestApp.overrideBaseUrl(HttpUrl.parse(RESTMockServer.getUrl()));

    OkHttpClient client = HexocatTestApp.getInstance().appComponent().okHttpClient();
    IdlingResource okHttpIdlingResource = OkHttp3IdlingResource.create("okhttp", client);
    Espresso.registerIdlingResources(okHttpIdlingResource);
  }

  @Override
  public void onStart() {
    RxJavaPlugins.setInitComputationSchedulerHandler(
        Rx2Idler.create("rxjava-scheduler-computation"));
    RxJavaPlugins.setInitIoSchedulerHandler(
        Rx2Idler.create("rxjava-scheduler-io"));

    super.onStart();
  }
}
