package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.abishov.hexocat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class TrendingPagerView extends CoordinatorLayout {

    @BindView(R.id.tab_layout_trending_periods)
    TabLayout trendingTabLayout;

    @BindView(R.id.viewpager_trending)
    ViewPager trendingViewPager;

    public TrendingPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        trendingViewPager.setAdapter(pagerAdapter);
        trendingTabLayout.setupWithViewPager(trendingViewPager);
    }
}
