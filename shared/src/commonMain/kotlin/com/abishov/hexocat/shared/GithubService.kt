package com.abishov.hexocat.shared

import com.abishov.hexocat.shared.filters.SearchQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.github.SearchRepositoriesQuery
import com.github.SearchRepositoriesQuery.AsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class GithubService constructor(private val client: ApolloClient) {

    suspend fun search(
        query: SearchQuery,
        repositoriesCount: Int = 32,
        repositoryTopicsCount: Int = 2,
        mentionableUsersCount: Int = 4,
        ownerAvatarSize: Int = 256,
        contributorAvatarSize: Int = 128
    ): List<AsRepository> {
        val apolloQuery = SearchRepositoriesQuery(
            query = query.toString(),
            repositoriesCount = repositoriesCount,
            repositoryTopicsCount = repositoryTopicsCount,
            mentionableUsersCount = mentionableUsersCount,
            ownerAvatarSize = ownerAvatarSize,
            contributorAvatarSize = contributorAvatarSize
        )

        return client.query(apolloQuery).execute()
            .map { response ->
                val responseData = response.data?.search?.items ?: listOf()

                responseData.map {
                    it?.asRepository
                        ?: throw IllegalArgumentException("API response is not structured as expected")
                }
            }.single()
    }
}
