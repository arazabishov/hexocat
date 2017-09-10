package com.abishov.hexocat.models;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class PagerApiModel<T> {

    @NonNull
    @SerializedName("items")
    public abstract List<T> items();

    @NonNull
    @VisibleForTesting
    public static <E> PagerApiModel<E> create(@NonNull List<E> items) {
        return new AutoValue_PagerApiModel<>(Collections.unmodifiableList(new ArrayList<>(items)));
    }

    @NonNull
    public static <T> TypeAdapter<PagerApiModel<T>> typeAdapter(@NonNull Gson gson,
            @NonNull TypeToken<? extends PagerApiModel<T>> typeToken) {
        return new AutoValue_PagerApiModel.GsonTypeAdapter<>(gson, typeToken);
    }
}
