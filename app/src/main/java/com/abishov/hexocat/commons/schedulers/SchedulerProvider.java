package com.abishov.hexocat.commons.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Used to decouple schedulers from RxJava chains in order to make
 * them unit-testable.
 */
public interface SchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
