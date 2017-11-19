package com.abishov.hexocat.home;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.abishov.hexocat.HexocatInstrumentationTestApp;
import com.squareup.spoon.SpoonRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockWebServer;

public final class HomeScreenTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class, false, false);

    @Rule
    public SpoonRule spoonRule = new SpoonRule();

    @Rule
    public MockWebServer mockWebServer = new MockWebServer();

    private HomeRobot homeRobot;

    @Before
    public void setUp() throws Exception {
        homeRobot = new HomeRobot();

        HexocatInstrumentationTestApp hexocat = (HexocatInstrumentationTestApp)
                InstrumentationRegistry.getTargetContext().getApplicationContext();
        hexocat.overrideBaseUrl(mockWebServer.url("/mock/"));

        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void clickOnTrendingTabMustNavigateToTrendingScreen() {
        spoonRule.screenshot(activityTestRule.getActivity(), "state_before");

        homeRobot.navigateToTrendingScreen()
                .someAction();

        spoonRule.screenshot(activityTestRule.getActivity(), "state_after");
    }
}
