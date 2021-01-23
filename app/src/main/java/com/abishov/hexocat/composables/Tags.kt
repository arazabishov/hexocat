package com.abishov.hexocat.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalLayout::class)
fun Tags(
  languages: List<LanguageViewModel>,
  topics: List<TopicViewModel>,
  modifier: Modifier
) {
  Box(modifier = modifier) {
    FlowRow(mainAxisSpacing = 16.dp, crossAxisSpacing = 8.dp) {
      languages.forEach { Language(it) }
      topics.forEach { Topic(it) }
    }
  }
}
