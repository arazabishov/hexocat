package com.abishov.hexocat.home.trending

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.abishov.hexocat.common.dispatcher.TestDispatcherProvider
import com.abishov.hexocat.common.livedata.captureValues
import com.abishov.hexocat.common.rule.TestCoroutineScopeRule
import com.abishov.hexocat.components.*
import com.abishov.hexocat.github.filters.SearchQuery
import com.github.TrendingRepositoriesQuery.*
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDate

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TrendingViewModelUnitTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineScope = TestCoroutineScopeRule()

  @Mock
  private lateinit var trendingService: TrendingService
  private lateinit var searchQuery: SearchQuery

  private lateinit var repositories: List<AsRepository>
  private lateinit var repositoryViewModels: List<RepositoryViewModel>

  private lateinit var trendingViewModel: TrendingViewModel

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this).close()

    val testDispatcherProvider = TestDispatcherProvider(coroutineScope.dispatcher)
    trendingViewModel = TrendingViewModel(testDispatcherProvider, trendingService)

    val owner = Owner(
      id = "test_owner_id",
      login = "test_login",
      avatarUrl = Uri.parse("http://github.com/test_avatar_url")
    )

    val topics = RepositoryTopics(
      topics = listOf(
        Topic(topic = Topic1(name = "test_topic_1")),
        Topic(topic = Topic1(name = "test_topic_2"))
      )
    )

    repositories = listOf(
      AsRepository(
        name = "test_repository_one",
        url = Uri.parse("http://github.com/test_html_url_one"),
        stargazerCount = 10,
        description = "test_description_one",
        openGraphImageUrl = Uri.parse("http://github.com/test_banner_url_one"),
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = topics,
        primaryLanguage = PrimaryLanguage(name = "test_lang_1", color = "test_lang_color_1"),
        mentionableUsers = MentionableUsers(
          contributors = listOf(
            Contributor(
              id = "test_contrib_1",
              avatarUrl = Uri.parse("http://github.com/test_avatar_url_contrib_1")
            )
          ), totalCount = 1
        )
      ),
      AsRepository(
        name = "test_repository_two",
        url = Uri.parse("http://github.com/test_html_url_two"),
        stargazerCount = 11,
        description = "test_description_two",
        openGraphImageUrl = Uri.parse("http://github.com/test_banner_url_two"),
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = RepositoryTopics(topics = listOf()),
        primaryLanguage = PrimaryLanguage(name = "test_lang_2", color = "test_lang_color_2"),
        mentionableUsers = MentionableUsers(
          contributors = listOf(
            Contributor(
              id = "test_contrib_2",
              avatarUrl = Uri.parse("http://github.com/test_avatar_url_contrib_2")
            )
          ), totalCount = 1
        )
      )
    )

    repositoryViewModels = listOf(
      RepositoryViewModel(
        name = "test_repository_one",
        description = "test_description_one",
        stars = 10,
        owner = OwnerViewModel(
          avatarUrl = Uri.parse("http://github.com/test_avatar_url"),
          login = "test_login",
          id = "test_owner_id"
        ),
        url = Uri.parse("http://github.com/test_html_url_one"),
        bannerUrl = Uri.parse("http://github.com/test_banner_url_one"),
        topics = listOf(
          TopicViewModel(name = "test_topic_1"),
          TopicViewModel(name = "test_topic_2")
        ),
        primaryLanguage = LanguageViewModel(name = "test_lang_1", color = "test_lang_color_1"),
        mentionableUsers = MentionableUsersViewModel(
          listOf(
            ContributorViewModel(
              id = "test_contrib_1",
              avatarUrl = Uri.parse("http://github.com/test_avatar_url_contrib_1")
            )
          ), 1
        )
      ),
      RepositoryViewModel(
        name = "test_repository_two",
        description = "test_description_two",
        stars = 11,
        owner = OwnerViewModel(
          avatarUrl = Uri.parse("http://github.com/test_avatar_url"),
          login = "test_login",
          id = "test_owner_id",
        ),
        url = Uri.parse("http://github.com/test_html_url_two"),
        bannerUrl = Uri.parse("http://github.com/test_banner_url_two"),
        topics = listOf(),
        primaryLanguage = LanguageViewModel(name = "test_lang_2", color = "test_lang_color_2"),
        mentionableUsers = MentionableUsersViewModel(
          listOf(
            ContributorViewModel(
              id = "test_contrib_2",
              avatarUrl = Uri.parse("http://github.com/test_avatar_url_contrib_2")
            )
          ), 1
        )
      )
    )

    searchQuery = SearchQuery(LocalDate.parse("2017-08-10"))
  }

  @Test
  fun `view model must propagate correct states on success`() = coroutineScope.runBlockingTest {
    whenever(trendingService.search(searchQuery, 32))
      .thenReturn(flowOf(repositories))

    trendingViewModel.screenState.captureValues {
      trendingViewModel.fetchRepositories(searchQuery)
      assertThat(values.size).isEqualTo(2)

      val viewStateProgress = values[0]
      assertThat(viewStateProgress!!).isInstanceOf(TrendingViewState.InProgress::class)

      val viewStateSuccess = values[1]
      assertThat(viewStateSuccess!!).isInstanceOf(TrendingViewState.Success::class)
      assertThat((viewStateSuccess as TrendingViewState.Success).items).isEqualTo(
        repositoryViewModels
      )
    }
  }

  @Test
  fun `view model must propagate correct states on failure`() = coroutineScope.runBlockingTest {
    whenever(trendingService.search(searchQuery, 32))
      .thenReturn(flow { error("test_message") })

    trendingViewModel.screenState.captureValues {
      trendingViewModel.fetchRepositories(searchQuery)
      assertThat(values.size).isEqualTo(2)

      val viewStateProgress = values[0]
      assertThat(viewStateProgress!!).isInstanceOf(TrendingViewState.InProgress::class)

      val viewStateFailure = values[1]
      assertThat(viewStateFailure!!).isInstanceOf(TrendingViewState.Failure::class)
      assertThat((viewStateFailure as TrendingViewState.Failure).error).isEqualTo("test_message")
    }
  }

  @Test
  fun `view model must propagate correct states when no items`() = coroutineScope.runBlockingTest {
    whenever(trendingService.search(searchQuery, 32))
      .thenReturn(flowOf(listOf()))

    trendingViewModel.screenState.captureValues {
      trendingViewModel.fetchRepositories(searchQuery)
      assertThat(values.size).isEqualTo(2)

      val viewStateProgress = values[0]
      assertThat(viewStateProgress!!).isInstanceOf(TrendingViewState.InProgress::class)

      val viewStateSuccess = values[1]
      assertThat(viewStateSuccess!!).isInstanceOf(TrendingViewState.Success::class)
      assertThat((viewStateSuccess as TrendingViewState.Success).items).isEmpty()
    }
  }
}
