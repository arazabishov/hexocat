package com.abishov.hexocat.home.repository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.abishov.hexocat.common.espresso.DrawableMatcher.withCompoundDrawable;
import static com.abishov.hexocat.common.espresso.RecyclerViewMatcher.atPosition;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

import android.view.View;
import android.widget.LinearLayout;
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
        withId(R.id.textview_repository_name), withText(name)
    )));
    return this;
  }

  public RepositoryItemRobot withDescription(String description) {
    matchesAtPosition(withChild(allOf(
        withId(R.id.textview_repository_description),
        withText(description)
    )));
    return this;
  }

  public RepositoryItemRobot withStars(int stars) {
    matchesAtPosition(withChild(allOf(instanceOf(LinearLayout.class),
        withChild(allOf(
            withParent(instanceOf(LinearLayout.class)),
            withId(R.id.textview_repository_stars),
            withText(String.valueOf(stars)),
            withCompoundDrawable(R.drawable.ic_star)
        )))
    ));
    return this;
  }

  public RepositoryItemRobot withForks(int forks) {
    matchesAtPosition(withChild(allOf(instanceOf(LinearLayout.class),
        withChild(allOf(
            withId(R.id.textview_repository_forks),
            withText(String.valueOf(forks)),
            withCompoundDrawable(R.drawable.ic_fork)
        )))
    ));
    return this;
  }

  public RepositoryItemRobot clickOnRow() {
    onView(recyclerViewMatcher)
        .perform(actionOnItemAtPosition(position, click()));
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
