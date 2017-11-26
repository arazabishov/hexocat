package com.abishov.hexocat.home;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.abishov.hexocat.common.espresso.BottomNavigationViewMatcher.isNavigationItemChecked;

import com.abishov.hexocat.R;
import com.abishov.hexocat.home.trending.TrendingRobot;

public final class HomeRobot {

  public TrendingRobot navigateToTrendingScreen() {
    try {
      onView(withId(R.id.item_trending)).check(matches(isNavigationItemChecked()));
    } catch (AssertionError assertionError) {
      onView(withId(R.id.item_trending)).perform(click()).check(matches(isNavigationItemChecked()));
    }

    return new TrendingRobot();
  }
}
