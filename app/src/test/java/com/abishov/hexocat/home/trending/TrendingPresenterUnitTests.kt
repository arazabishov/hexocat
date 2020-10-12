package com.abishov.hexocat.home.trending

import assertk.assert
import assertk.assertions.*
import com.abishov.hexocat.common.schedulers.TrampolineSchedulersProvider
import com.abishov.hexocat.composables.LanguageViewModel
import com.abishov.hexocat.composables.TopicViewModel
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.composables.RepositoryViewModel
import com.github.TrendingRepositoriesQuery.*
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.BehaviorSubject
import org.assertj.core.api.Java6Assertions.assertThat
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

    val languages = Languages(
      edges = listOf(
        Edge2(node = Node1(name = "test_lang_1", color = "test_lang_color_1")),
        Edge2(node = Node1(name = "test_lang_2", color = "test_lang_color_2"))
      )
    )

    repositories = listOf(
      AsRepository(
        name = "test_repository_one",
        url = "test_html_url_one",
        forkCount = 5,
        stargazers = Stargazers(totalCount = 10),
        description = "test_description_one",
        openGraphImageUrl = "test_banner_url_one",
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = topics,
        languages = languages
      ),
      AsRepository(
        name = "test_repository_two",
        url = "test_html_url_two",
        forkCount = 7,
        stargazers = Stargazers(totalCount = 11),
        description = "test_description_two",
        openGraphImageUrl = "test_banner_url_two",
        usesCustomOpenGraphImage = true,
        owner = owner,
        repositoryTopics = RepositoryTopics(edges = listOf()),
        languages = Languages(edges = listOf())
      )
    )

    repositoryViewModels = listOf(
      RepositoryViewModel(
        name = "test_repository_one",
        description = "test_description_one",
        forks = "5",
        stars = "10",
        avatarUrl = "http://github.com/test_avatar_url?s=128",
        login = "test_login",
        url = "test_html_url_one",
        bannerUrl = "test_banner_url_one",
        usesBannerUrl = true,
        topics = listOf(
          TopicViewModel(name = "test_topic_1"),
          TopicViewModel(name = "test_topic_2")
        ),
        languages = listOf(
          LanguageViewModel(name = "test_lang_1", color = "test_lang_color_1"),
          LanguageViewModel(name = "test_lang_2", color = "test_lang_color_2")
        )
      ),
      RepositoryViewModel(
        name = "test_repository_two",
        description = "test_description_two",
        forks = "7",
        stars = "11",
        avatarUrl = "http://github.com/test_avatar_url?s=128",
        login = "test_login",
        url = "test_html_url_two",
        bannerUrl = "test_banner_url_two",
        usesBannerUrl = true,
        topics = listOf(),
        languages = listOf()
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
    assertThat(viewQueries.hasObservers()).isTrue

    viewQueries.onNext(searchQuery)
    listResults.onNext(repositories)
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assert(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateSuccess = repositoriesConsumer.allValues[1]
    assert(viewStateSuccess).isInstanceOf(TrendingViewState.Success::class)
    assert((viewStateSuccess as TrendingViewState.Success).items).isEqualTo(repositoryViewModels)
  }

  @Test
  @Throws(Exception::class)
  fun `presenter must propagate correct states on failure`() {
    trendingPresenter.onAttach(trendingView)
    assertThat(viewQueries.hasObservers()).isTrue

    viewQueries.onNext(searchQuery)
    listResults.onError(Throwable("test_message"))
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assert(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateFailure = repositoriesConsumer.allValues[1]
    assert(viewStateFailure).isInstanceOf(TrendingViewState.Failure::class)
  }

  @Test
  @Throws(Exception::class)
  fun `presenter must propagate correct states when no items`() {
    trendingPresenter.onAttach(trendingView)
    assert(viewQueries.hasObservers()).isTrue()

    viewQueries.onNext(searchQuery)
    listResults.onNext(ArrayList())
    listResults.onComplete()

    verify(trendingView.bindTo(), times(2))
      .accept(repositoriesConsumer.capture())

    val viewStateProgress = repositoriesConsumer.allValues[0]
    assert(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateSuccess = repositoriesConsumer.allValues[1]
    assert(viewStateSuccess).isInstanceOf(TrendingViewState.Success::class)
    assert((viewStateSuccess as TrendingViewState.Success).items).isEmpty()
  }

  @Test
  fun `presenter must unsubscribe from view on detach`() {
    assert(listResults.hasObservers()).isFalse()
    assert(viewQueries.hasObservers()).isFalse()

    trendingPresenter.onAttach(trendingView)
    assert(listResults.hasObservers()).isFalse()
    assert(viewQueries.hasObservers()).isTrue()

    trendingPresenter.onDetach()
    assert(listResults.hasObservers()).isFalse()
    assert(viewQueries.hasObservers()).isFalse()
  }
}
