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
      number_of_repos = count
    )

    return Rx2Apollo.from(client.query(apolloQuery))
      .map { response ->
        val responseData = response.data?.search?.edges ?: listOf()

        responseData.map {
          it?.node?.asRepository
            ?: throw IllegalArgumentException("API response is not structured as expected")
        }
      }
  }
}
