package com.abishov.hexocat.home.trending;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class RepositoryViewModel implements Parcelable {

    abstract String name();

    abstract String description();

    abstract String forks();

    abstract String stars();

    abstract String avatarUrl();

    static RepositoryViewModel create(String name, String description,
            String forks, String stars, String ownerAvatar) {
        return new AutoValue_RepositoryViewModel(name, description, forks, stars, ownerAvatar);
    }
}

