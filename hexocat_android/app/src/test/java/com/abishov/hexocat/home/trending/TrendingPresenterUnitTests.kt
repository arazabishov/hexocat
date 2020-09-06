package com.abishov.hexocat.home.trending

import assertk.assert
import assertk.assertions.*
import com.abishov.hexocat.common.schedulers.TrampolineSchedulersProvider
import com.abishov.hexocat.github.filters.Order
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.github.filters.Sort
import com.abishov.hexocat.home.repository.RepositoryViewModel
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
      login = "test_login",
      url = "test_html_url",
      avatarUrl = "test_avatar_url"
    )

    repositories = Arrays.asList(
      AsRepository(
        name = "test_repository_one",
        url = "test_html_url_one",
        forkCount = 5,
        stargazers = Stargazers(totalCount = 10),
        description = "test_description_one",
        owner = owner
      ),
      AsRepository(
        name = "test_repository_two",
        url = "test_html_url_two",
        forkCount = 7,
        stargazers = Stargazers(totalCount = 11),
        description = "test_description_two",
        owner = owner
      )
    )

    repositoryViewModels = Arrays.asList(
      RepositoryViewModel(
        name = "test_repository_one",
        description = "test_description_one",
        forks = "5",
        stars = "10",
        avatarUrl = "test_avatar_url",
        login = "test_login",
        url = "test_html_url_one"
      ),
      RepositoryViewModel(
        name = "test_repository_two",
        description = "test_description_two",
        forks = "7",
        stars = "11",
        avatarUrl = "test_avatar_url",
        login = "test_login",
        url = "test_html_url_two"
      )
    )

    searchQuery = SearchQuery(LocalDate.parse("2017-08-10"))
    whenever(trendingView.searchQueries()).thenReturn(viewQueries)
    whenever(trendingService.search(searchQuery, 64)).thenReturn(
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
    assert(viewStateProgress).isInstanceOf(TrendingViewState.InProgress::class)

    val viewStateSuccess = repositoriesConsumer.allValues[1]
    assert(viewStateSuccess).isInstanceOf(TrendingViewState.Success::class)
    assert((viewStateSuccess as TrendingViewState.Success).items).isEqualTo(repositoryViewModels)
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
