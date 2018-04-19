package com.abishov.hexocat.github

import assertk.assert
import assertk.assertions.isEqualTo
import com.abishov.hexocat.Inject
import org.junit.Test

class RepositoryIntegrationTests {

  @Test
  fun `payload must map to model correctly`() {
    val adapter = Inject.moshi()
      .adapter(Repository::class.java)

    val repository = adapter.fromJson(
      """
        {
          "id": 892275,
          "name": "retrofit",
          "forks": 222,
          "stargazers_count": 777,
          "html_url": "https://github.com/square/retrofit",
          "description": "Type-safe HTTP client",
          "owner": {
            "login": "square",
            "id": 82592,
            "avatar_url": "https://avatars0.githubusercontent.com/u/82592?v=4",
            "gravatar_id": "",
            "url": "https://api.github.com/users/square",
            "html_url": "https://github.com/square",
            "followers_url": "https://api.github.com/users/square/followers",
            "following_url": "https://api.github.com/users/square/following{/other_user}",
            "gists_url": "https://api.github.com/users/square/gists{/gist_id}",
            "starred_url": "https://api.github.com/users/square/starred{/owner}{/repo}",
            "subscriptions_url": "https://api.github.com/users/square/subscriptions",
            "organizations_url": "https://api.github.com/users/square/orgs",
            "repos_url": "https://api.github.com/users/square/repos",
            "events_url": "https://api.github.com/users/square/events{/privacy}",
            "received_events_url": "https://api.github.com/users/square/received_events",
            "type": "User",
            "site_admin": false
          }
        }
        """
    )!!

    assert(repository.name).isEqualTo("retrofit")
    assert(repository.forks).isEqualTo(222)
    assert(repository.stars).isEqualTo(777)
    assert(repository.htmlUrl).isEqualTo("https://github.com/square/retrofit")
    assert(repository.description).isEqualTo("Type-safe HTTP client")
    assert(repository.owner.login).isEqualTo("square")
    assert(repository.owner.htmlUrl).isEqualTo("https://github.com/square")
  }
}
