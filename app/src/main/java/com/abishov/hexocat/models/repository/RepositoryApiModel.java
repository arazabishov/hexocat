package com.abishov.hexocat.models.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abishov.hexocat.models.organization.OrganizationApiModel;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RepositoryApiModel {
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

    @Nullable
    @SerializedName(DESCRIPTION)
    public abstract String description();

    @NonNull
    @SerializedName(OWNER)
    public abstract OrganizationApiModel owner();

    @NonNull
    public static RepositoryApiModel create(@NonNull String name, @NonNull String htmlUrl,
            @Nullable String description, @NonNull OrganizationApiModel owner) {
        return new AutoValue_RepositoryApiModel(name, htmlUrl, description, owner);
    }

    @NonNull
    public static TypeAdapter<RepositoryApiModel> typeAdapter(@NonNull Gson gson) {
        return new AutoValue_RepositoryApiModel.GsonTypeAdapter(gson);
    }
}
