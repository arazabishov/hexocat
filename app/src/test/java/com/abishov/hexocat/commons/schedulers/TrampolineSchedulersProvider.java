package com.abishov.hexocat.commons.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public final class TrampolineSchedulersProvider implements SchedulerProvider {

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
