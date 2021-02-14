package com.abishov.hexocat.shared

import com.abishov.hexocat.shared.network.Authorization
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
actual class GithubServiceFactory {

    @ExperimentalCoroutinesApi
    actual fun create(): GithubService {
        val apolloClient = ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                serverUrl = "https://api.github.com/graphql",
                headers = Authorization.headers,
            )
        )

        return GithubService(apolloClient)
    }
}
