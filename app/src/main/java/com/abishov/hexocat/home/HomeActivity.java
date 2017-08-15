package com.abishov.hexocat.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abishov.hexocat.home.trending.TrendingFragment;

public final class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, TrendingFragment.create())
                    .commitNow();
        }
    }
}
