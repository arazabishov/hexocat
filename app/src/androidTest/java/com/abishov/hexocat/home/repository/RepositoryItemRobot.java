package com.abishov.hexocat.home.repository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.abishov.hexocat.common.espresso.RecyclerViewMatcher.atPosition;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

import android.view.View;
import com.abishov.hexocat.R;
import org.hamcrest.Matcher;

public final class RepositoryItemRobot {

  private final Matcher<View> recyclerViewMatcher;
  private final int position;

  public RepositoryItemRobot(Matcher<View> recyclerViewMatcher, int position) {
    this.recyclerViewMatcher = recyclerViewMatcher;
    this.position = position;
  }

  public RepositoryItemRobot withName(String name) {
    matchesAtPosition(withChild(allOf(
        withId(R.id.textview_repository_name),
        withText(name)
    )));
    return this;
  }

  private void matchesAtPosition(Matcher<View> matcher) {
    onView(recyclerViewMatcher)
        .perform(scrollToPosition(position))
        .check(matches(atPosition(position, allOf(
            instanceOf(RepositoryItemView.class), matcher
        ))));
  }
}
