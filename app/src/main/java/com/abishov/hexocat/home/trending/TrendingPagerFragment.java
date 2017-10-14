package com.abishov.hexocat.home.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.BaseFragment;

public final class TrendingPagerFragment extends BaseFragment {

    public static TrendingPagerFragment create() {
        return new TrendingPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trending_pager_view, container, false);
    }
}
