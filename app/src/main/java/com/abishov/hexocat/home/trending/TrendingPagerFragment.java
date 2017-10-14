package com.abishov.hexocat.home.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TrendingPagerView trendingPagerView = (TrendingPagerView) view;

        PagerAdapter pagerAdapter = new TrendingPagerAdapter(
                getContext(), getChildFragmentManager());
        trendingPagerView.setAdapter(pagerAdapter);
    }
}
