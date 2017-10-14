package com.abishov.hexocat.models.repository;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.abishov.hexocat.models.organization.OrganizationApiModel;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RepositoryApiModel {
    private static final String NAME = "name";
    private static final String HTML_URL = "html_url";
    private static final String FORKS = "forks";
    private static final String STARGAZERS_COUNT = "stargazers_count";
    private static final String DESCRIPTION = "description";
    private static final String OWNER = "owner";

    @SerializedName(NAME)
    public abstract String name();

    @SerializedName(HTML_URL)
    public abstract String htmlUrl();

    @SerializedName(FORKS)
    public abstract Integer forks();

    @SerializedName(STARGAZERS_COUNT)
    public abstract Integer stars();

    @Nullable
    @SerializedName(DESCRIPTION)
    public abstract String description();

    @SerializedName(OWNER)
    public abstract OrganizationApiModel owner();

    @VisibleForTesting
    public static RepositoryApiModel create(String name, String htmlUrl, Integer forks,
            Integer stars, @Nullable String description, OrganizationApiModel owner) {
        return new AutoValue_RepositoryApiModel(name, htmlUrl, forks, stars, description, owner);
    }

    public static TypeAdapter<RepositoryApiModel> typeAdapter(Gson gson) {
        return new AutoValue_RepositoryApiModel.GsonTypeAdapter(gson);
    }
}
