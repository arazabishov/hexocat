package com.abishov.hexocat.home.trending

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.abishov.hexocat.common.rule.MockWebServerRule
import com.abishov.hexocat.home.HomeActivity
import io.appflate.restmock.RESTMockServer.whenPOST
import io.appflate.restmock.RequestsVerifier.verifyPOST
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class TrendingScreenTest {

  @get:Rule
  val activityTestRule = IntentsTestRule(
    HomeActivity::class.java, false, false
  )

  @get:Rule
  val mockWebServerRule = MockWebServerRule()

  @Test
  fun mustRenderTrendingRepositoriesForToday() {
    whenPOST(pathContains("graphql"))
      .thenReturnFile("response/search/repositories/200_trending_today.json")

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      repositoryAt(0) {
        name("awesome-php-migrations")
        stars(74)
        forks(1)
        description(
          "migrify — Awesome sources for PHP projects migrations - legacy, " +
              "pattern refactoring, framework switches, temlates and configs..."
        )
      }

      repositoryAt(1) {
        name("gbajs2")
        stars(51)
        forks(6)
        description(
          "andychase — gbajs2 is a Game Boy Advance emulator written in Javascript " +
              "from scratch using HTML5 technologies like Canvas and Web Audio. It is freely " +
              "licensed and works in any modern browser without plugins."
        )
      }

      repositoryAt(2) {
        name("asyncio-buffered-pipeline")
        stars(33)
        forks(0)
        description(
          "michalc — Utility function to parallelise pipelines " +
              "of Python asyncio iterators/generators"
        )
      }
    }

    verifyPOST(pathContains("graphql")).atLeast(1)
  }

  @Test
  fun mustRender400ErrorMessage() {
    whenPOST(pathContains("graphql")).thenReturnEmpty(400)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 400 ")
      retryButtonIsVisible()
    }

    verifyPOST(pathContains("graphql")).atLeast(1)
  }

  @Test
  fun mustRender500ErrorMessage() {
    whenPOST(pathContains("graphql")).thenReturnEmpty(500)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 500 ")
      retryButtonIsVisible()
    }

    verifyPOST(pathContains("graphql")).atLeast(1)
  }

  @Test
  fun mustRequestBackendAfterRetryButtonClicked() {
    whenPOST(pathContains("graphql")).thenReturnEmpty(400)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 400 ")
      retryButtonIsVisible()
      retry()
    }

    verifyPOST(pathContains("graphql")).atLeast(2)
  }

  @Test
  fun mustNavigateToBrowserOnRepositoryItemClicked() {
    whenPOST(pathContains("graphql"))
      .thenReturnFile("response/search/repositories/200_trending_today.json")

    activityTestRule.launchActivity(Intent())

    intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))

    trendingScreen {
      repositoryAt(0) { clickOnRow() }
      repositoryAt(1) { clickOnRow() }
      repositoryAt(2) { clickOnRow() }
    }

    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/migrify/awesome-php-migrations")
      )
    )
    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/andychase/gbajs2")
      )
    )
    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/michalc/asyncio-buffered-pipeline")
      )
    )

    verifyPOST(pathContains("graphql")).atLeast(1)
  }
}
