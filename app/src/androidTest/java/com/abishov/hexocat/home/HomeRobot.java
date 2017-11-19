package com.abishov.hexocat.home;

import com.abishov.hexocat.home.trending.TrendingRobot;

public final class HomeRobot {
    public TrendingRobot navigateToTrendingScreen() {
        // TODO: find a solution for double-click of trending item
        // onView(withId(R.id.item_trending)).perform(click());
        return new TrendingRobot();
    }
}
