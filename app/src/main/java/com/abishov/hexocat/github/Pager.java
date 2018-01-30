package com.abishov.hexocat.github;

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
public abstract class Pager<T> {

  @VisibleForTesting
  public static <E> Pager<E> create(List<E> items) {
    return new AutoValue_Pager<>(Collections.unmodifiableList(new ArrayList<>(items)));
  }

  public static <T> TypeAdapter<Pager<T>> typeAdapter(Gson gson,
      TypeToken<? extends Pager<T>> typeToken) {
    return new AutoValue_Pager.GsonTypeAdapter<>(gson, typeToken);
  }

  @SerializedName("items")
  public abstract List<T> items();
}
