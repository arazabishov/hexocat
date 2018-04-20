package com.abishov.hexocat.github

import assertk.assert
import assertk.assertions.isEqualTo
import com.abishov.hexocat.Inject
import org.junit.Test

class UserIntegrationTests {

  @Test
  fun `payload must map to model correctly`() {
    val adapter = Inject.moshi()
      .adapter(User::class.java)

    val user = adapter.fromJson(
      """
        {
          "login": "CaMnter",
          "id": 10336931,
          "avatar_url": "https://avatars0.githubusercontent.com/u/10336931?v=3",
          "gravatar_id": "",
          "url": "https://api.github.com/users/CaMnter",
          "html_url": "https://github.com/CaMnter",
          "followers_url": "https://api.github.com/users/CaMnter/followers",
          "following_url": "https://api.github.com/users/CaMnter/following{/other_user}",
          "gists_url": "https://api.github.com/users/CaMnter/gists{/gist_id}",
          "starred_url": "https://api.github.com/users/CaMnter/starred{/owner}{/repo}",
          "subscriptions_url": "https://api.github.com/users/CaMnter/subscriptions",
          "organizations_url": "https://api.github.com/users/CaMnter/orgs",
          "repos_url": "https://api.github.com/users/CaMnter/repos",
          "events_url": "https://api.github.com/users/CaMnter/events{/privacy}",
          "received_events_url": "https://api.github.com/users/CaMnter/received_events",
          "type": "User",
          "site_admin": false
        }
        """
    )!!

    assert(user.login).isEqualTo("CaMnter")
    assert(user.htmlUrl).isEqualTo("https://github.com/CaMnter")
    assert(user.avatarUrl).isEqualTo("https://avatars0.githubusercontent.com/u/10336931?v=3")
  }
}
