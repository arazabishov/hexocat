package com.abishov.hexocat.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalLayout::class)
fun Contributors(users: ContributorsViewModel, modifier: Modifier) {
  val topContributors = users.contributors
  val contributorsOverflow = users.totalCount - topContributors.size

  Box(modifier) {
    FlowRow(mainAxisSpacing = 4.dp, crossAxisSpacing = 8.dp) {
      topContributors.forEach { Contributor(it) }

      if (contributorsOverflow > 0) {
        ContributorOverflow(contributorsOverflow)
      }
    }
  }
}
