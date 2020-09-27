package com.abishov.hexocat.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Repositories(
  repos: List<RepositoryViewModel>,
  onRepositoryClick: (RepositoryViewModel) -> Unit
) {
  LazyColumnFor(repos, modifier = Modifier.padding(vertical = 4.dp)) {
    RepositoryItem(repository = it, onRepositoryClick = onRepositoryClick)
  }
}
