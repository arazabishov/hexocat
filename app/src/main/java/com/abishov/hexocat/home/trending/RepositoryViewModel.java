package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class RepositoryViewModel {

    @NonNull
    abstract String name();

    @NonNull
    abstract String description();

    @NonNull
    static RepositoryViewModel create(@NonNull String name, @NonNull String description) {
        return new AutoValue_RepositoryViewModel(name, description);
    }
}

