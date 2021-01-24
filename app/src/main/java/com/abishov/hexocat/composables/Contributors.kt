package com.abishov.hexocat.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalLayout::class)
fun Contributors(
  contributors: ContributorsViewModel,
  modifier: Modifier
) {
  val topContributors = contributors.contributors
  val contributorsOverflow = contributors.totalCount - topContributors.size

  Column(
    modifier = Modifier
      .wrapContentSize()
      .then(modifier)
  ) {

    FlowRow(mainAxisSpacing = 4.dp, crossAxisSpacing = 8.dp) {
      contributors.contributors.forEach { Contributor(it) }

      if (contributorsOverflow > 0) {
        ContributorOverflow(contributorsOverflow)
      }
    }
  }
}
