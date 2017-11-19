package com.abishov.hexocat;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.squareup.rx2.idler.Rx2Idler;

import io.reactivex.plugins.RxJavaPlugins;

public final class HexocatTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return Instrumentation.newApplication(HexocatTestApp.class, context);
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
