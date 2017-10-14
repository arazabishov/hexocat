package com.abishov.hexocat.home.trending;

import android.os.Parcelable;

import com.abishov.hexocat.commons.views.ViewState;
import com.abishov.hexocat.home.repository.RepositoryViewModel;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AutoValue
abstract class TrendingViewState implements ViewState, Parcelable {
    abstract List<RepositoryViewModel> items();


    static TrendingViewState idle() {
        return new AutoValue_TrendingViewState(true, false, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }


    static TrendingViewState progress() {
        return new AutoValue_TrendingViewState(false, true, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }


    static TrendingViewState success(List<RepositoryViewModel> items) {
        List<RepositoryViewModel> deepCopy = Collections.unmodifiableList(new ArrayList<>(items));
        return new AutoValue_TrendingViewState(false, false, true, false, "", deepCopy);
    }


    static TrendingViewState failure(Throwable throwable) {
        return new AutoValue_TrendingViewState(false, false, false, true, throwable.getMessage(),
                Collections.unmodifiableList(Collections.emptyList()));
    }
}
