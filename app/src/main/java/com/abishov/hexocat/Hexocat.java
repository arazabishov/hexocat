package com.abishov.hexocat;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.utils.CrashReportingTree;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import hu.supercluster.paperwork.Paperwork;
import timber.log.Timber;

public class Hexocat extends Application {

    @NonNull
    AppComponent appComponent;

    @NonNull
    RefWatcher refWatcher;

    @Inject
    Paperwork paperwork;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // this process is going to be used to
            // analyze heap dumps by LeakCanary
            return;
        }

        setupAppComponent();
        setUpLeakCanary();
        setUpTimber();

        // Do not allow to do any work on the
        // main thread. Detect activity leaks.
        setupStrictMode();
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

    private void setupAppComponent() {
        appComponent = prepareAppComponent();
        appComponent.inject(this);
    }

    private void setUpLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = RefWatcher.DISABLED;
        }
    }

    private void setUpTimber() {
        if (BuildConfig.DEBUG) {
            // Verbose logging for debug builds.
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree(paperwork));
        }
    }

    @NonNull
    protected AppComponent prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent appComponent() {
        return appComponent;
    }

    @NonNull
    public RefWatcher refWatcher() {
        return refWatcher;
    }
}
