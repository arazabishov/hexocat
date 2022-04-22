package com.abishov.hexocat.shared

import com.abishov.hexocat.shared.filters.SearchQuery
import com.abishov.hexocat.shared.models.*
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.github.SearchRepositoriesQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

private fun SearchRepositoriesQuery.AsRepository.toModel(): RepositoryModel {
    val primaryLanguage = primaryLanguage?.let {
        LanguageModel(it.name, it.color)
    }

    val topics = repositoryTopics.topics?.mapNotNull { node ->
        node?.let { TopicModel(it.topic.name) }
    }

    val contributors = mentionableUsers.contributors?.mapNotNull { node ->
        node?.let { ContributorModel(node.id, node.avatarUrl.toString()) }
    }

    val users = contributors?.let {
        MentionableUsersModel(it, mentionableUsers.totalCount)
    }

    val owner = OwnerModel(
        id = owner.id,
        login = owner.login,
        avatarUrl = owner.avatarUrl.toString()
    )

    val bannerUrl = if (usesCustomOpenGraphImage) openGraphImageUrl.toString() else null

    return RepositoryModel(
        name = name,
        overview = description,
        bannerUrl = bannerUrl,
        stars = stargazerCount,
        url = url.toString(),
        owner = owner,
        primaryLanguage = primaryLanguage,
        topics = topics,
        mentionableUsers = users,
    )
}

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
class GithubService constructor(private val client: ApolloClient) {

    suspend fun search(
        query: SearchQuery,
        repositoriesCount: Int = 32,
        repositoryTopicsCount: Int = 2,
        mentionableUsersCount: Int = 4,
        ownerAvatarSize: Int = 256,
        contributorAvatarSize: Int = 128
    ): List<RepositoryModel> {
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
                }.map { it.toModel() }
            }.single()
    }
}
