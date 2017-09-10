package com.abishov.hexocat.commons.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.util.AttributeSet;
import android.view.View;

/**
 * Simple layout behavior that will track the state of the AppBarLayout
 * and match its offset for a corresponding footer.
 */
public final class FooterBarBehavior extends Behavior<BottomNavigationView> {

    public FooterBarBehavior() {
        // Required to instantiate as a default behavior.
    }

    // Required to attach behavior via XML.
    public FooterBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // This is called to determine which views this behavior depends on.
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View view) {
        // We are watching changes in the AppBarLayout.
        return view instanceof AppBarLayout;
    }

    // This is called for each change to a dependent view.
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, BottomNavigationView child, View view) {
        int offset = -view.getTop();
        child.setTranslationY(offset);
        return true;
    }
}