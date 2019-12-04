package com.abishov.hexocat.common.espresso

import androidx.test.espresso.matcher.BoundedMatcher
import androidx.core.util.Preconditions.checkNotNull
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher

object RecyclerViewMatcher {

  fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    checkNotNull(itemMatcher)

    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
      override fun describeTo(description: Description) {
        description.appendText("has item at position $position: ")
        itemMatcher.describeTo(description)
      }

      override fun matchesSafely(view: RecyclerView): Boolean {
        val viewHolder = view.findViewHolderForAdapterPosition(position)
        return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
      }
    }
  }
}
