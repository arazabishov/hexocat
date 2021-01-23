package com.abishov.hexocat.composables

data class RepositoryViewModel(
  val name: String,
  val description: String,
  val forks: String,
  val stars: String,
  val avatarUrl: String,
  val bannerUrl: String,
  val usesBannerUrl: Boolean,
  val login: String,
  val url: String,
  val languages: List<LanguageViewModel>,
  val topics: List<TopicViewModel>
)
