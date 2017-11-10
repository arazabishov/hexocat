package com.abishov.hexocat.home;

import android.support.test.rule.ActivityTestRule;

import com.squareup.spoon.SpoonRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Rule
    public SpoonRule spoonRule = new SpoonRule();

    private HomeRobot homeRobot;

    @Before
    public void setUp() throws Exception {
        homeRobot = new HomeRobot();
    }

    @Test
    public void clickOnTrendingTabMustNavigateToTrendingScreen() {
        spoonRule.screenshot(activityTestRule.getActivity(), "state_before");

        homeRobot.navigateToTrendingScreen()
                .someAction();

        spoonRule.screenshot(activityTestRule.getActivity(), "state_after");
    }
}
