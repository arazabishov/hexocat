package com.abishov.hexocat.android.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abishov.hexocat.shared.models.TopicModel

@Composable
fun Topic(topic: TopicModel, modifier: Modifier = Modifier) {
    Text(
        text = "#${topic.name}",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.caption,
        modifier = modifier
    )
}
