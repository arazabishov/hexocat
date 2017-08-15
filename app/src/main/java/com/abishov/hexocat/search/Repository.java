package com.abishov.hexocat.search;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Repository {
    private static final String NAME = "name";
    private static final String HTML_URL = "html_url";
    private static final String DESCRIPTION = "description";
    private static final String OWNER = "owner";

    @NonNull
    @SerializedName(NAME)
    public abstract String name();

    @NonNull
    @SerializedName(HTML_URL)
    public abstract String htmlUrl();

    @NonNull
    @SerializedName(DESCRIPTION)
    public abstract String description();

    @NonNull
    @SerializedName(OWNER)
    public abstract Organization owner();

    @NonNull
    public static Repository create(@NonNull String name, @NonNull String htmlUrl,
            @NonNull String description, @NonNull Organization owner) {
        return new AutoValue_Repository(name, htmlUrl, description, owner);
    }

    @NonNull
    public static TypeAdapter<Repository> typeAdapter(@NonNull Gson gson) {
        return new AutoValue_Repository.GsonTypeAdapter(gson);
    }
}
