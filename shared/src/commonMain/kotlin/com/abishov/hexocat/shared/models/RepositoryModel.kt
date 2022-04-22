package com.abishov.hexocat.shared.models

data class RepositoryModel(
    val name: String,
    val overview: String?,
    val bannerUrl: String?,
    val stars: Int,
    val url: String,
    val owner: OwnerModel,
    val primaryLanguage: LanguageModel?,
    val topics: List<TopicModel>?,
    val mentionableUsers: MentionableUsersModel?
)
