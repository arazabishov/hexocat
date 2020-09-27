package com.abishov.hexocat.home.trending

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abishov.hexocat.R
import com.abishov.hexocat.common.theme.HexocatTheme
import com.abishov.hexocat.composables.Repositories
import com.abishov.hexocat.composables.RepositoryViewModel

@Composable
fun Trending(
  state: TrendingViewState,
  onRepositoryClick: (RepositoryViewModel) -> Unit,
  onRetry: () -> Unit
) {
  HexocatTheme {
    when (state) {
      is TrendingViewState.Success -> {
        Repositories(state.items, onRepositoryClick)
      }
      is TrendingViewState.InProgress -> {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          CircularProgressIndicator(color = MaterialTheme.colors.secondary)
        }
      }
      is TrendingViewState.Failure -> {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = state.error,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(16.dp)
          )
          OutlinedButton(onClick = onRetry) {
            Text(text = stringResource(id = R.string.home_action_retry))
          }
        }
      }
    }
  }
}
