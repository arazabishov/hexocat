package com.abishov.hexocat.common.espresso;

import static org.hamcrest.core.Is.is;

import android.support.design.internal.BottomNavigationItemView;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class BottomNavigationViewMatcher {

  public static Matcher<View> isNavigationItemChecked() {
    return withNavigationItemState(is(true));
  }

  public static Matcher<View> withNavigationItemState(final Matcher<Boolean> isChecked) {
    return new BoundedMatcher<View, BottomNavigationItemView>(BottomNavigationItemView.class) {

      @Override
      public void describeTo(Description description) {
        description.appendText("with navigation item state: ");
        isChecked.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(BottomNavigationItemView item) {
        return isChecked.matches(item.getItemData().isChecked());
      }
    };
  }
}
