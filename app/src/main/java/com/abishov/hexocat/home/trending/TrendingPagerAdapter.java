package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.abishov.hexocat.R;

import java.util.Arrays;
import java.util.List;

final class TrendingPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Period> periods;

    TrendingPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        periods = Arrays.asList(
                new Period(context.getString(R.string.trending_today), 1),
                new Period(context.getString(R.string.trending_last_week), 7),
                new Period(context.getString(R.string.trending_last_month), 30));
    }

    @Override
    public Fragment getItem(int position) {
        return TrendingFragment.create(periods.get(position).days);
    }

    @Override
    public int getCount() {
        return periods.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return periods.get(position).label;
    }

    static class Period {
        final String label;
        final int days;

        Period(String label, int days) {
            this.label = label;
            this.days = days;
        }
    }
}
