package com.abishov.hexocat.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.abishov.hexocat.R;
import com.abishov.hexocat.home.trending.TrendingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.toolbar_home)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation_home)
    BottomNavigationView bottomNavigationView;

    private Fragment trendingFragment;
    private Fragment favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupToolbar();
        setupNavigationMenu(savedInstanceState);
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.app_name);
    }

    private void setupNavigationMenu(Bundle savedInstanceState) {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> matchFragment(item.getItemId()));

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.item_trending);
        }
    }

    private boolean matchFragment(@IdRes int fragmentId) {
        if (fragmentId == R.id.item_trending) {
            trendingFragment = getSupportFragmentManager()
                    .findFragmentByTag(TrendingFragment.TAG);

            if (trendingFragment == null) {
                trendingFragment = TrendingFragment.create();
            }

            attachFragment(trendingFragment, TrendingFragment.TAG);
        } else {
            favoritesFragment = getSupportFragmentManager()
                    .findFragmentByTag(TAG);

            if (favoritesFragment == null) {
                favoritesFragment = new Fragment();
            }

            attachFragment(favoritesFragment, TAG);
        }

        return true;
    }

    private void attachFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_content, fragment, tag)
                .commitNow();
    }
}
