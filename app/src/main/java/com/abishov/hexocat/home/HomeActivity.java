package com.abishov.hexocat.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.abishov.hexocat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class HomeActivity extends AppCompatActivity {

    @BindView(R.id.framelayout_content)
    ViewGroup contentView;

    @BindView(R.id.bottom_navigation_home)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupNavigationMenu(savedInstanceState);
    }

    private void setupNavigationMenu(Bundle savedInstanceState) {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> matchView(item.getItemId()));

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.item_trending);
        }
    }

    private boolean matchView(@LayoutRes int viewId) {
        if (viewId == R.id.item_trending) {
            View trendingPagerView = getLayoutInflater().inflate(
                    R.layout.trending_pager_view, contentView, false);
            swapView(trendingPagerView);
        } else {
            swapView(new FrameLayout(this));
        }

        return true;
    }

    private void swapView(View view) {
        contentView.removeAllViews();
        contentView.addView(view);
    }
}
