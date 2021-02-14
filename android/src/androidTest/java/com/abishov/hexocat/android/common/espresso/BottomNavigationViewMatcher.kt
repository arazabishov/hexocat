package com.abishov.hexocat.android.common.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.core.Is.`is`

object BottomNavigationViewMatcher {

    val isNavigationItemChecked: Matcher<View>
        get() = withNavigationItemState(`is`(true))

    private fun withNavigationItemState(isChecked: Matcher<Boolean>): Matcher<View> {
        return object :
            BoundedMatcher<View, BottomNavigationItemView>(BottomNavigationItemView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with navigation item state: ")
                isChecked.describeTo(description)
            }

            override fun matchesSafely(item: BottomNavigationItemView): Boolean {
                return isChecked.matches(item.itemData.isChecked)
            }
        }
    }
}
