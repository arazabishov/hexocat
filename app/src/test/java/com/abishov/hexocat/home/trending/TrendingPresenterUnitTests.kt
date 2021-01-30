package com.abishov.hexocat.home.trending

import assertk.assertThat
import assertk.assertions.*
import com.abishov.hexocat.common.schedulers.TrampolineSchedulersProvider
import com.abishov.hexocat.composables.ContributorsViewModel
import com.abishov.hexocat.composables.LanguageViewModel
import com.abishov.hexocat.composables.RepositoryViewModel
import com.abishov.hexocat.composables.TopicViewModel
import com.abishov.hexocat.github.filters.SearchQuery
import com.github.TrendingRepositoriesQuery.*
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.threeten.bp.LocalDate
import java.util.*

class TrendingPresenterUnitTests {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private lateinit var trendingView: TrendingContract.View

  @Mock
  private lateinit var trendingService: TrendingService

  @Captor
  private lateinit var repositoriesConsumer: ArgumentCaptor<TrendingViewState>

  private lateinit var viewQueries: BehaviorSubject<SearchQuery>
  private lateinit var listResults: BehaviorSubject<List<AsRepository>>
  private lateinit var searchQuery: SearchQuery

  private lateinit var repositories: List<AsRepository>
  private lateinit var repositoryViewModels: List<RepositoryViewModel>

  private lateinit var trendingPresenter: TrendingPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    viewQueries = BehaviorSubject.create()
    listResults = BehaviorSubject.create()

    trendingPresenter = TrendingPresenter(
      TrampolineSchedulersProvider(), trendingService
    )

    val owner = Owner(
      id = "test_owner_id",
      url = "test_html_url",
      login = "test_login",
      avatarUrl = "http://github.com/test_avatar_url"
    )

    val topics = RepositoryTopics(
      edges = listOf(
        Edge1(node = Node(topic = Topic(name = "test_topic_1"))),
        Edge1(node = Node(topic = Topic(name = "test_topic_2")))
      )
    )

    repositories = listOf(
      AsRepository(
        name = "test_repository_one",
        url = "test_html_url_one",
        stargazerCount = 10,
        description = "test_description_one",
        openGraphImageUrl = "test_banner_url_one",
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = topics,
        primaryLanguage = PrimaryLanguage(name = "test_lang_1", color = "test_lang_color_1"),
        mentionableUsers = MentionableUsers(nodes = listOf(), totalCount = 0)
      ),
      AsRepository(
        name = "test_repository_two",
        url = "test_html_url_two",
        stargazerCount = 11,
        description = "test_description_two",
        openGraphImageUrl = "test_banner_url_two",
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = RepositoryTopics(edges = listOf()),
        primaryLanguage = PrimaryLanguage(name = "test_lang_2", color = "test_lang_color_2"),
        mentionableUsers = MentionableUsers(nodes = listOf(), totalCount = 0)
      )
    )

    repositoryViewModels = listOf(
      RepositoryViewModel(
        name = "test_repository_one",
        description = "test_description_one",
        stars = "10",
        avatarUrl = "http://github.com/test_avatar_url?s=128",
        login = "test_login",
        url = "test_html_url_one",
        bannerUrl = "test_banner_url_one",
        usesBannerUrl = true,
        ownerId = "test_owner_id",
        topics = listOf(
          TopicViewModel(name = "test_topic_1"),
          TopicViewModel(name = "test_topic_2")
        ),
        languages = listOf(LanguageViewModel(name = "test_lang_1", color = "test_lang_color_1")),
        contributors = ContributorsViewModel(listOf(), 0)
      ),
      RepositoryViewModel(
        name = "test_repository_two",
        description = "test_description_two",
        stars = "11",
        avatarUrl = "http://github.com/test_avatar_url?s=128",
        login = "test_login",
        url = "test_html_url_two",
        bannerUrl = "test_banner_url_two",
        usesBannerUrl = true,
        ownerId = "test_owner_id",
        topics = listOf(),
        languages = listOf(LanguageViewModel(name = "test_lang_2", color = "test_lang_color_2")),
        contributors = ContributorsViewModel(listOf(), 0)
      )
    )

    searchQuery = SearchQuery(LocalDate.parse("2017-08-10"))
    whenever(trendingView.searchQueries()).thenReturn(viewQueries)
    whenever(trendingService.search(searchQuery, 32)).thenReturn(
      listResults
    )
  }

  @Test
  @Throws(Exception::class)
  fun `presenter must propagate correct states on success`() {
    trendingPresenter.onAttach(trendingView)
    assertThat(viewQueries.hasObservers()).isTrue()

    viewQueries.onNext(searchQuery)
    listResults.onNext(repositories)
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assertThat(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateSuccess = repositoriesConsumer.allValues[1]
    assertThat(viewStateSuccess).isInstanceOf(TrendingViewState.Success::class)
    assertThat((viewStateSuccess as TrendingViewState.Success).items).isEqualTo(repositoryViewModels)
  }

  @Test
  @Throws(Exception::class)
  fun `presenter must propagate correct states on failure`() {
    trendingPresenter.onAttach(trendingView)
    assertThat(viewQueries.hasObservers()).isTrue()

    viewQueries.onNext(searchQuery)
    listResults.onError(Throwable("test_message"))
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assertThat(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateFailure = repositoriesConsumer.allValues[1]
    assertThat(viewStateFailure).isInstanceOf(TrendingViewState.Failure::class)
  }

  @Test
  @Throws(Exception::class)
  fun `presenter must propagate correct states when no items`() {
    trendingPresenter.onAttach(trendingView)
    assertThat(viewQueries.hasObservers()).isTrue()

    viewQueries.onNext(searchQuery)
    listResults.onNext(ArrayList())
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assertThat(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateSuccess = repositoriesConsumer.allValues[1]
    assertThat(viewStateSuccess).isInstanceOf(TrendingViewState.Success::class)
    assertThat((viewStateSuccess as TrendingViewState.Success).items).isEmpty()
  }

  @Test
  fun `presenter must unsubscribe from view on detach`() {
    assertThat(listResults.hasObservers()).isFalse()
    assertThat(viewQueries.hasObservers()).isFalse()

    trendingPresenter.onAttach(trendingView)
    assertThat(listResults.hasObservers()).isFalse()
    assertThat(viewQueries.hasObservers()).isTrue()

    trendingPresenter.onDetach()
    assertThat(listResults.hasObservers()).isFalse()
    assertThat(viewQueries.hasObservers()).isFalse()
  }
}