package com.abishov.hexocat.home.trending

import com.abishov.hexocat.github.filters.SearchQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.github.TrendingRepositoriesQuery
import com.github.TrendingRepositoriesQuery.AsRepository
import io.reactivex.Observable
import javax.inject.Inject

class TrendingService @Inject constructor(private val client: ApolloClient) {

  fun search(query: SearchQuery, count: Int): Observable<List<AsRepository>> {
    val apolloQuery = TrendingRepositoriesQuery(
      query = query.toString(),
      number_of_repositories = 2,
      number_of_repository_topics = 2,
      number_of_mentionable_users = 4,
      owner_avatar_size = 256,
      contributor_avatar_size = 128
    )

    return Rx2Apollo.from(client.query(apolloQuery))
      .map { response ->
        val responseData = response.data?.search?.items ?: listOf()

        responseData.map {
          it?.asRepository
            ?: throw IllegalArgumentException("API response is not structured as expected")
        }
      }
  }
}
