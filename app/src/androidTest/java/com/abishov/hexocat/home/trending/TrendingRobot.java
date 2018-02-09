package com.abishov.hexocat.home.trending;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.abishov.hexocat.common.espresso.CustomConstraintViewAction.withCustomConstraints;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import com.abishov.hexocat.R;
import com.abishov.hexocat.home.repository.RepositoryItemRobot;

public final class TrendingRobot {

  public TrendingRobot retry() {
    onView(allOf(isDisplayed(), withId(R.id.button_retry))).perform(click());
    return this;
  }

  public TrendingRobot pullToRefresh() {
    onView(allOf(isDisplayed(), withId(R.id.recyclerview_trending)))
        .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)));
    return this;
  }

  public TrendingRobot withTrendingTab(@IdRes int tab) {
    onView(allOf(isDescendantOfA(instanceOf(TabLayout.class)), withText(tab))).perform(click());
    return this;
  }

  public TrendingRobot swipeTrendingPagerLeft() {
    onView(withId(R.id.viewpager_trending))
        .perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(85)));
    return this;
  }

  public TrendingRobot withErrorMessage(String error) {
    onView(allOf(withId(R.id.textview_error), isDisplayed())).check(matches(withText(error)));
    return this;
  }

  public TrendingRobot withRetryButtonVisible() {
    onView(allOf(isDisplayed(), withId(R.id.button_retry))).check(matches(isDisplayed()));
    return this;
  }

  public RepositoryItemRobot withRepositoryItemAt(int position) {
    return new RepositoryItemRobot(allOf(
        withId(R.id.recyclerview_trending), isDisplayed()
    ), position);
  }
}
