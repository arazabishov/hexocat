package com.abishov.hexocat.home.trending;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TrendingViewModel implements Parcelable {

    public abstract String name();

    public abstract String description();

    public abstract String forks();

    public abstract String stars();

    public abstract String avatarUrl();

    public abstract String login();

    public static TrendingViewModel create(String name, String description,
            String forks, String stars, String ownerAvatar, String login) {
        return new AutoValue_TrendingViewModel(name, description, forks,
                stars, ownerAvatar, login);
    }
}

