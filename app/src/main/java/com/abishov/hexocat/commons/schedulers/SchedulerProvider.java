package com.abishov.hexocat.commons.schedulers;

import io.reactivex.Scheduler;

/**
 * Used to decouple schedulers from RxJava chains in order to make
 * them unit-testable.
 */
public interface SchedulerProvider {
    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
