package com.abishov.hexocat.home.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.BaseFragment;

import butterknife.BindView;

public final class TrendingPagerFragment extends BaseFragment {
    public static final String TAG = TrendingPagerFragment.class.getSimpleName();

    @BindView(R.id.tab_layout_trending_periods)
    TabLayout trendingTabLayout;

    @BindView(R.id.viewpager_trending)
    ViewPager trendingViewPager;

    public static TrendingPagerFragment create() {
        return new TrendingPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bind(this, view);

        FragmentStatePagerAdapter pagerAdapter = new TrendingPagerAdapter(
                getActivity(), getChildFragmentManager());
        trendingViewPager.setAdapter(pagerAdapter);
        trendingTabLayout.setupWithViewPager(trendingViewPager);
    }
}
