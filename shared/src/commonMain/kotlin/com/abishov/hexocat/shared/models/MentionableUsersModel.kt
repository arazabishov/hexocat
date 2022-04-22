package com.abishov.hexocat.shared.models

data class MentionableUsersModel(
    val contributors: List<ContributorModel>,
    val totalCount: Int
)