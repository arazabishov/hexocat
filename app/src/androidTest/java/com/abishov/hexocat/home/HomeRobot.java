package com.abishov.hexocat.home;

import com.abishov.hexocat.R;
import com.abishov.hexocat.home.trending.TrendingRobot;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public final class HomeRobot {
    public TrendingRobot navigateToTrendingScreen() {
        onView(withId(R.id.item_trending)).perform(click());
        return new TrendingRobot();
    }
}
