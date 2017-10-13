package com.abishov.hexocat.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.abishov.hexocat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class HomeActivity extends AppCompatActivity {
    private static final String STATE_MENU_ITEM_ID = "state:menuItemId";

    @BindView(R.id.framelayout_content)
    ViewGroup contentView;

    @BindView(R.id.bottom_navigation_home)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> swapView(item.getItemId()));

        int selectedNavigationItemId = savedInstanceState == null ?
                R.id.item_trending : savedInstanceState.getInt(STATE_MENU_ITEM_ID);
        swapView(selectedNavigationItemId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_MENU_ITEM_ID, bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    private boolean swapView(@IdRes int viewId) {
        contentView.removeAllViews();
        if (viewId == R.id.item_trending) {
            View trendingPagerView = getLayoutInflater().inflate(
                    R.layout.trending_pager_view, contentView, false);
            contentView.addView(trendingPagerView);
        }

        return true;
    }
}
