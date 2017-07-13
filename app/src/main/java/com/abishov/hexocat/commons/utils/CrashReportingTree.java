package com.abishov.hexocat.commons.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Locale;

import hu.supercluster.paperwork.Paperwork;
import timber.log.Timber;

public final class CrashReportingTree extends Timber.Tree {

    @NonNull
    private final Paperwork paperwork;

    public CrashReportingTree(@NonNull Paperwork paperwork) {
        this.paperwork = paperwork;
    }

    @Override
    protected void log(int priority, String tag,
            @NonNull String message, @Nullable Throwable throwable) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        if (priority == Log.ERROR && throwable != null) {
            // log error using crash reporting tool
            Timber.tag(String.format(Locale.US, "Tag=[%s], sha=[%s], date=[%s]", tag,
                    paperwork.get("gitSha"), paperwork.get("buildDate")));
            Timber.e(throwable, message);
        }
    }
}
