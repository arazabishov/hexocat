package com.abishov.hexocat.shared

import com.abishov.hexocat.shared.network.Authorization
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.HttpMethod
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Headers.Companion.toHeaders
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

@OptIn(ApolloExperimental::class, ExperimentalCoroutinesApi::class)
actual class GithubServiceFactory(
    private val serverUrl: HttpUrl,
    private val okHttpClient: OkHttpClient
) {

    actual fun create(): GithubService {
        val httpHeaders = Authorization.headers.toHeaders()
        val apolloClient = ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                serverUrl = serverUrl,
                headers = httpHeaders,
                httpCallFactory = okHttpClient,
                httpMethod = HttpMethod.Post
            )
        )

        return GithubService(apolloClient)
    }
}
