package com.abishov.hexocat.composables

import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Topic(topic: TopicViewModel, modifier: Modifier = Modifier) {
  Text(
    text = "#${topic.name}",
    color = MaterialTheme.colors.secondaryVariant,
    style = MaterialTheme.typography.caption,
    modifier = modifier
  )
}
