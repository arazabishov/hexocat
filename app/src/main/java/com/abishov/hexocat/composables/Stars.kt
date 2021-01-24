package com.abishov.hexocat.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// a unicode star has no additional spacing compared to
// the vector icons provided by material design library
@Composable
private fun Star(modifier: Modifier = Modifier) {
  Text(
    text = "☆",
    color = MaterialTheme.colors.secondaryVariant,
    style = MaterialTheme.typography.caption,
    modifier = modifier
  )
}

@Composable
fun Stars(stars: String, modifier: Modifier = Modifier) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    Star(modifier = Modifier.padding(end = 6.dp))
    Text(text = stars, style = MaterialTheme.typography.caption)
  }
}
