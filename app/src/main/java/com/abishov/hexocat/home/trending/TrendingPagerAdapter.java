package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.abishov.hexocat.R;

import java.util.Arrays;
import java.util.List;

final class TrendingPagerAdapter extends FragmentStatePagerAdapter {
    private final List<String> pagerTabs;

    public TrendingPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        pagerTabs = Arrays.asList(
                context.getString(R.string.trending_today),
                context.getString(R.string.trending_last_week),
                context.getString(R.string.trending_last_month));
    }

    @Override
    public Fragment getItem(int position) {
        return TrendingFragment.create();
    }

    @Override
    public int getCount() {
        return pagerTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTabs.get(position);
    }
}
