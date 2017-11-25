package com.abishov.hexocat.home.trending;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

import com.abishov.hexocat.R;
import com.abishov.hexocat.home.repository.RepositoryItemRobot;

public final class TrendingRobot {

  public TrendingRobot retry() {
    onView(withId(R.id.button_retry)).perform(click());
    return this;
  }

  public TrendingRobot pullToRefresh() {
    onView(withId(R.id.recyclerview_trending)).perform(swipeUp());
    return this;
  }

  public RepositoryItemRobot withRepositoryItemAt(int position) {
    return new RepositoryItemRobot(allOf(
        withId(R.id.recyclerview_trending), isDisplayed()
    ), position);
  }
}
