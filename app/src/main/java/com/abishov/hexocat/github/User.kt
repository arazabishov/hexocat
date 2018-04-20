package com.abishov.hexocat.github

import com.squareup.moshi.Json

data class User(
  @Json(name = "login") val login: String,
  @Json(name = "html_url") val htmlUrl: String,
  @Json(name = "avatar_url") val avatarUrl: String
)