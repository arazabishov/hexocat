package com.abishov.hexocat.android.home.trending

import com.abishov.hexocat.github.filters.SearchQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toFlow
import com.github.TrendingRepositoriesQuery
import com.github.TrendingRepositoriesQuery.AsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingService @Inject constructor(private val client: ApolloClient) {

  fun search(query: SearchQuery, count: Int): Flow<List<AsRepository>> {
    val apolloQuery = TrendingRepositoriesQuery(
      query = query.toString(),
      number_of_repositories = count,
      number_of_repository_topics = 2,
      number_of_mentionable_users = 4,
      owner_avatar_size = 256,
      contributor_avatar_size = 128
    )

    return client.query(apolloQuery).toFlow().map { response ->
      val responseData = response.data?.search?.items ?: listOf()

      responseData.map {
        it?.asRepository
          ?: throw IllegalArgumentException("API response is not structured as expected")
      }
    }
  }
}
