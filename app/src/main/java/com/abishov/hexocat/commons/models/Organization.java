package com.abishov.hexocat.commons.models;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Organization {
    private static final String LOGIN = "login";
    private static final String HTML_URL = "html_url";
    private static final String AVATAR_URL = "avatar_url";

    @NonNull
    @SerializedName(LOGIN)
    public abstract String login();

    @NonNull
    @SerializedName(HTML_URL)
    public abstract String htmlUrl();

    @NonNull
    @SerializedName(AVATAR_URL)
    public abstract String avatarUrl();

    @NonNull
    public static Organization create(@NonNull String login, @NonNull String htmlUrl,
                                      @NonNull String avatarUrl) {
        return new AutoValue_Organization(login, htmlUrl, avatarUrl);
    }

    @NonNull
    public static TypeAdapter<Organization> typeAdapter(@NonNull Gson gson) {
        return new AutoValue_Organization.GsonTypeAdapter(gson);
    }
}
