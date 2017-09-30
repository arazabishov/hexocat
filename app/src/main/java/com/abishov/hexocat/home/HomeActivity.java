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
    private static final String TAG = HomeActivity.class.getSimpleName();

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
                item -> matchFragment(item.getItemId()));

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.item_trending);
        }
    }

    private boolean matchFragment(@IdRes int fragmentId) {
        if (fragmentId == R.id.item_trending) {
            attachFragment(TrendingPagerFragment.create(), TrendingPagerFragment.TAG);
        } else {
            attachFragment(new Fragment(), TAG);
        }

        return true;
    }

    private void attachFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_content, fragment, tag)
                .commitNow();
    }
}
