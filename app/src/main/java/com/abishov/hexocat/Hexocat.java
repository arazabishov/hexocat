package com.abishov.hexocat;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;
import com.abishov.hexocat.common.utils.CrashReportingTree;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import hu.supercluster.paperwork.Paperwork;
import javax.inject.Inject;
import okhttp3.HttpUrl;
import org.threeten.bp.Clock;
import timber.log.Timber;

public class Hexocat extends Application implements HasActivityInjector {

  protected AppComponent appComponent;
  protected RefWatcher refWatcher;

  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Inject
  Paperwork paperwork;

  @SuppressWarnings("NullAway")
  @Override
  public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // this process is going to be used to
      // analyze heap dumps by LeakCanary
      return;
    }

    AndroidThreeTen.init(this);

    setupAppComponent();
    setUpLeakCanary();
    setUpTimber();

    // Do not allow to do any work on the
    // main thread. Detect activity leaks.
    setupStrictMode();
  }

  @Override
  public AndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  private void setupStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .build());
    }
  }

  protected void setupAppComponent() {
    appComponent = prepareAppComponent();
    appComponent.inject(this);
  }

  protected void setUpLeakCanary() {
    if (BuildConfig.DEBUG) {
      refWatcher = LeakCanary.install(this);
    } else {
      refWatcher = RefWatcher.DISABLED;
    }
  }

  protected void setUpTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new CrashReportingTree(paperwork));
    }
  }

  protected AppComponent prepareAppComponent() {
    return DaggerAppComponent.builder()
        .baseUrl(HttpUrl.parse("http://api.github.com"))
        .clock(Clock.systemDefaultZone())
        .application(this)
        .build();
  }

  public AppComponent appComponent() {
    return appComponent;
  }

  public RefWatcher refWatcher() {
    return refWatcher;
  }
}
