package com.abishov.hexocat.github

import com.squareup.moshi.Json

data class Repository(
  @Json(name = "name") val name: String,
  @Json(name = "html_url") val htmlUrl: String,
  @Json(name = "forks") val forks: Int,
  @Json(name = "stargazers_count") val stars: Int,
  @Json(name = "description") val description: String?,
  @Json(name = "owner") val owner: User
)