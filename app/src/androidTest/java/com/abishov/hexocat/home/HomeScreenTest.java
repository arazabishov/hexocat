package com.abishov.hexocat.home;

import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.utils.RequestMatchers.hasExactQueryParameters;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.HexocatTestApp;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import com.abishov.hexocat.home.trending.TrendingRobot;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
import io.appflate.restmock.utils.QueryParam;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

  @Rule
  public final ActivityTestRule<HomeActivity> activityRule =
      CaptureScreenshotsRule.builder(HomeActivity.class)
          .initialTouchMode()
          .build();

  private HomeRobot homeRobot;

  @Before
  public void setUp() throws Exception {
    HexocatTestApp.overrideBaseUrl(HttpUrl.parse(RESTMockServer.getUrl()));
    RESTMockServer.reset();

    OkHttpClient client = HexocatTestApp.getInstance().appComponent().okHttpClient();
    Espresso.registerIdlingResources(OkHttp3IdlingResource.create("okhttp", client));

    homeRobot = new HomeRobot();
  }

  @Test
  @CaptureScreenshots
  public void mustRenderTrendingRepositoriesForToday() {
    whenGET(pathContains("search/repositories"))
        .thenReturnFile("search/repositories/200_trending_today.json");

    activityRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen();

    trendingRobot.withRepositoryItemAt(0)
        .withName("charts");
    trendingRobot.withRepositoryItemAt(1)
        .withName("state-of-the-art-result-for-machine-learning-problems");
    trendingRobot.withRepositoryItemAt(2)
        .withName("hangzhouYunQi2017ppt");
    trendingRobot.withRepositoryItemAt(3)
        .withName("bottery");
    trendingRobot.withRepositoryItemAt(4)
        .withName("deepo");

    RequestsVerifier.verifyGET(allOf(pathContains("search/repositories"), hasExactQueryParameters(
        new QueryParam("q", "created:>=2017-08-29"),
        new QueryParam("sort", "watchers"),
        new QueryParam("order", "desc"))
    )).exactly(1);

    RequestsVerifier.verifyGET(allOf(pathContains("search/repositories"), hasExactQueryParameters(
        new QueryParam("q", "created:>=2017-08-23"),
        new QueryParam("sort", "watchers"),
        new QueryParam("order", "desc"))
    )).exactly(1);
  }
}
