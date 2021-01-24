package com.abishov.hexocat.composables

data class RepositoryViewModel(
  val name: String,
  val description: String,
  val stars: String,
  val avatarUrl: String,
  val bannerUrl: String,
  val usesBannerUrl: Boolean,
  val ownerId: String,
  val login: String,
  val url: String,
  val languages: List<LanguageViewModel>,
  val topics: List<TopicViewModel>,
  val contributors: ContributorsViewModel
)
