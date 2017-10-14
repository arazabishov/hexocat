package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abishov.hexocat.R;

import java.util.Arrays;
import java.util.List;

final class TrendingPagerAdapter extends PagerAdapter {
    private final LayoutInflater layoutInflater;
    private final List<Period> periods;

    TrendingPagerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        periods = Arrays.asList(
                new Period(context.getString(R.string.trending_today), 1),
                new Period(context.getString(R.string.trending_last_week), 7),
                new Period(context.getString(R.string.trending_last_month), 30));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TrendingView viewGroup = (TrendingView) layoutInflater.inflate(
                R.layout.trending_view, container, false);
        container.addView(viewGroup);
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return periods.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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
