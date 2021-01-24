package com.abishov.hexocat.composables

data class ContributorsViewModel(val contributors: List<ContributorViewModel>, val totalCount: Int)
data class ContributorViewModel(val id: String, val avatarUrl: String)
