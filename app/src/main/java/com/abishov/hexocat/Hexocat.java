package com.abishov.hexocat;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class Hexocat extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // this process is going to be used to
            // analyze heap dumps by LeakCanary
            return;
        }

        // Do not allow to do any work on the
        // main thread. Detect activity leaks.
        setUpStrictMode();
    }

    private void setUpStrictMode() {
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

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
