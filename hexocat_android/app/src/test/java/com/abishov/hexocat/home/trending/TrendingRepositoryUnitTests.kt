package com.abishov.hexocat.home.trending

import com.abishov.hexocat.github.Pager
import com.abishov.hexocat.github.Repository
import com.abishov.hexocat.github.User
import com.abishov.hexocat.github.filters.Order
import com.abishov.hexocat.github.filters.SearchQuery
import com.abishov.hexocat.github.filters.Sort
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDate
import java.util.*

class TrendingRepositoryUnitTests {

  @Mock
  private lateinit var trendingService: TrendingService

  private lateinit var trendingRepository: TrendingRepository

  private lateinit var subject: PublishSubject<Pager<Repository>>

  private lateinit var repositoryPager: Pager<Repository>
  private lateinit var searchQuery: SearchQuery

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    subject = PublishSubject.create()
    trendingRepository = TrendingRepository(trendingService)

    val owner = User(
      login = "test_login",
      htmlUrl = "test_html_url",
      avatarUrl = "test_avatar_url"
    )

    repositoryPager = Pager(
      Arrays.asList(
        Repository(
          name = "test_repository_one",
          htmlUrl = "test_html_url_one",
          forks = 5,
          stars = 10,
          description = "test_description_one",
          owner = owner
        ),
        Repository(
          name = "test_repository_two",
          htmlUrl = "test_html_url_two",
          forks = 4,
          stars = 3,
          description = "test_description_two",
          owner = owner
        )
      )
    )

    searchQuery = SearchQuery.Builder()
      .createdSince(LocalDate.parse("2017-08-10"))
      .build()

    whenever(
      trendingService.repositories(searchQuery, Sort.STARS, Order.DESC)
    ).thenReturn(subject)
  }

  @Test
  fun `trending repositories must call trending service with correct filter`() {
    val testObserver = trendingRepository.trendingRepositories(searchQuery).test()

    subject.onNext(repositoryPager)
    subject.onComplete()

    testObserver.assertNoErrors()
    testObserver.assertComplete()
    testObserver.assertValueCount(1)

    assertThat(testObserver.values()[0]).isEqualTo(repositoryPager.items)

    verify(trendingService, times(1))
      .repositories(searchQuery, Sort.STARS, Order.DESC)
  }
}
