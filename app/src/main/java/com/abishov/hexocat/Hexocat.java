package com.abishov.hexocat;

import android.app.Application;
import android.os.StrictMode;

import com.abishov.hexocat.commons.network.NetworkComponent;
import com.abishov.hexocat.commons.network.NetworkModule;
import com.abishov.hexocat.commons.picasso.PicassoComponent;
import com.abishov.hexocat.commons.utils.CrashReportingTree;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import hu.supercluster.paperwork.Paperwork;
import timber.log.Timber;

public class Hexocat extends Application {
    protected AppComponent appComponent;
    protected NetworkComponent networkComponent;
    protected PicassoComponent picassoComponent;
    protected RefWatcher refWatcher;

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

        AndroidThreeTen.init(this);

        setupAppComponent();
        setupNetworkComponent();
        setupPicassoComponent();
        setUpLeakCanary();
        setUpTimber();

        // Do not allow to do any work on the
        // main thread. Detect activity leaks.
        setupStrictMode();
    }

    protected void setupStrictMode() {
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

    protected void setupNetworkComponent() {
        networkComponent = appComponent.plus(new NetworkModule());
    }

    protected void setupPicassoComponent() {
        picassoComponent = networkComponent.picassoComponent();
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
            // Verbose logging for debug builds.
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree(paperwork));
        }
    }

    protected AppComponent prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    public NetworkComponent networkComponent() {
        return networkComponent;
    }

    public PicassoComponent picassoComponent() {
        return picassoComponent;
    }

    public RefWatcher refWatcher() {
        return refWatcher;
    }
}
