package com.abishov.hexocat.common.espresso

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.IdRes
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher

object DrawableMatcher {

  fun withCompoundDrawable(@IdRes resourceId: Int): Matcher<View> {
    return object : BoundedMatcher<View, TextView>(TextView::class.java) {
      override fun describeTo(description: Description) {
        description.appendText("has compound drawable resource $resourceId")
      }

      public override fun matchesSafely(textView: TextView): Boolean {
        for (drawable in textView.compoundDrawables) {
          if (sameBitmap(textView.context, drawable, resourceId)) {
            return true
          }
        }
        return false
      }
    }
  }

  private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
    return sameBitmap(drawable, context.resources.getDrawable(resourceId))
  }

  private fun sameBitmap(first: Drawable?, second: Drawable?): Boolean {
    var first = first
    var second = second
    if (first == null || second == null) {
      return false
    }
    if (first is StateListDrawable && second is StateListDrawable) {
      first = first.current
      second = second.current
    }
    if (first is BitmapDrawable) {
      val bitmap = first.bitmap
      val otherBitmap = (second as BitmapDrawable).bitmap
      return bitmap.sameAs(otherBitmap)
    }
    return false
  }
}
