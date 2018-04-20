package com.abishov.hexocat.home.repository

data class RepositoryViewModel(
  val name: String,
  val description: String,
  val forks: String,
  val stars: String,
  val avatarUrl: String,
  val login: String,
  val url: String
)
