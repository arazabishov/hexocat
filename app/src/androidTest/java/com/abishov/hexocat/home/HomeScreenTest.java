package com.abishov.hexocat.home;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.HexocatInstrumentationTestApp;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

  @Rule
  public ActivityTestRule<HomeActivity> activityTestRule =
      new CaptureScreenshotsRule<>(HomeActivity.class, false, false);

  @Rule
  public MockWebServer mockWebServer = new MockWebServer();

  private HomeRobot homeRobot;

  @Before
  public void setUp() throws Exception {
    homeRobot = new HomeRobot();

    HexocatInstrumentationTestApp hexocat =
        (HexocatInstrumentationTestApp) InstrumentationRegistry.getTargetContext()
            .getApplicationContext();
    hexocat.overrideBaseUrl(mockWebServer.url("/mock/"));

    activityTestRule.launchActivity(new Intent());
  }

  @Test
  @CaptureScreenshots
  public void clickOnTrendingTabMustNavigateToTrendingScreen() {
    homeRobot.navigateToTrendingScreen().someAction();
  }
}
