package com.abishov.hexocat.home.trending

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.ui.test.createAndroidComposeRule
import com.abishov.hexocat.HexocatTestApp
import com.abishov.hexocat.common.rule.MockWebServerRule
import com.abishov.hexocat.home.HomeActivity
import io.appflate.restmock.RESTMockServer.whenPOST
import io.appflate.restmock.RequestsVerifier.verifyPOST
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class TrendingScreenTest {
  private lateinit var context: Context
  private val composeTestRule = createAndroidComposeRule<HomeActivity>()

  @get:Rule
  val chainedRule: RuleChain = RuleChain.outerRule(MockWebServerRule())
    .around(composeTestRule)

  private fun trendingScreen(func: TrendingRobot.() -> Unit) {
    trendingScreen(composeTestRule, context, func)
  }

  @Before
  fun setUp() {
    context = HexocatTestApp.instance
    Intents.init()
  }

  @After
  fun tearDown() {
    Intents.release()
  }

  @Test
  fun mustRenderTrendingRepositoriesForToday() {
    whenPOST(pathContains("graphql"))
      .thenReturnFile("response/search/repositories/200_trending.json")

    trendingScreen {
      withRepository("AmnesiaTheDarkDescent") {
        owner("FrictionalGames")
        languages("CMake", "Objective-C", "Perl", "C")
      }

      withRepository("kb") {
        owner("gnebbia")
        description("A minimalist command line knowledge base manager")
        languages("Makefile", "Python", "Shell", "Ruby")
        topics("knowledge", "cheatsheets", "procedures", "methodology")
      }

      withRepository("fastmac") {
        owner("fastai")
        description("Get a MacOS or Linux shell, for free, in around 2 minutes")
        languages("Shell")
        topics("tmate", "ssh", "workflow", "macos")
      }
    }

    verifyPOST(pathContains("graphql"))
  }

  @Test
  fun mustRender400ErrorMessage() {
    whenPOST(pathContains("graphql"))
      .thenReturnEmpty(400)

    trendingScreen {
      errorMessage("HTTP 400")
      retryButtonIsVisible()
    }

    verifyPOST(pathContains("graphql"))
  }

  @Test
  fun mustRender500ErrorMessage() {
    whenPOST(pathContains("graphql"))
      .thenReturnEmpty(500)

    trendingScreen {
      errorMessage("HTTP 500")
      retryButtonIsVisible()
    }

    verifyPOST(pathContains("graphql"))
  }

  @Test
  fun mustRequestBackendAfterRetryButtonClicked() {
    whenPOST(pathContains("graphql"))
      .thenReturnEmpty(400)

    trendingScreen {
      errorMessage("HTTP 400")
      retryButtonIsVisible()
      retry()

      // since mocked server returns 400, the
      // same result is expected
      errorMessage("HTTP 400")
      retryButtonIsVisible()
    }

    verifyPOST(pathContains("graphql"))
      .exactly(2)
  }

  @Test
  fun mustNavigateToBrowserOnRepositoryItemClicked() {
    val verifyClickedOn: (String) -> Unit = { url ->
      intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(url)))
    }

    whenPOST(pathContains("graphql"))
      .thenReturnFile("response/search/repositories/200_trending.json")

    intending(not(isInternal())).respondWith(
      Instrumentation.ActivityResult(
        Activity.RESULT_OK,
        null
      )
    )

    trendingScreen {
      withRepository("AmnesiaTheDarkDescent") { clickOnRow() }
      withRepository("kb") { clickOnRow() }
      withRepository("fastmac") { clickOnRow() }
    }

    verifyClickedOn("https://github.com/FrictionalGames/AmnesiaTheDarkDescent")
    verifyClickedOn("https://github.com/gnebbia/kb")
    verifyClickedOn("https://github.com/fastai/fastmac")

    verifyPOST(pathContains("graphql"))
  }
}
