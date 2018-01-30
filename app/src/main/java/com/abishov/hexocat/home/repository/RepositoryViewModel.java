package com.abishov.hexocat.home.repository;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RepositoryViewModel implements Parcelable {

  public static RepositoryViewModel create(String name, String description,
      String forks, String stars, String ownerAvatar, String login, String url) {
    return new AutoValue_RepositoryViewModel(name, description, forks,
        stars, ownerAvatar, login, url);
  }

  public abstract String name();

  public abstract String description();

  public abstract String forks();

  public abstract String stars();

  public abstract String avatarUrl();

  public abstract String login();

  public abstract String url();
}

