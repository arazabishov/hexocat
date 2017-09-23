package com.abishov.hexocat.home.trending;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class TrendingViewModel implements Parcelable {

    abstract String name();

    abstract String description();

    abstract String forks();

    abstract String stars();

    abstract String avatarUrl();

    abstract String login();

    static TrendingViewModel create(String name, String description,
            String forks, String stars, String ownerAvatar, String login) {
        return new AutoValue_TrendingViewModel(name, description, forks,
                stars, ownerAvatar, login);
    }
}

