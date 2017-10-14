package com.abishov.hexocat.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.abishov.hexocat.R;
import com.abishov.hexocat.home.trending.TrendingPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_home)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> swapFragment(item.getItemId()));
        if (savedInstanceState == null) {
            swapFragment(R.id.item_trending);
        }
    }

    private boolean swapFragment(@IdRes int viewId) {
        Fragment fragment;

        if (viewId == R.id.item_trending) {
            fragment = TrendingPagerFragment.create();
        } else {
            fragment = new Fragment();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.framelayout_content, fragment)
                .commitNow();

        return true;
    }
}
