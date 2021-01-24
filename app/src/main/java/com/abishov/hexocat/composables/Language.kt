package com.abishov.hexocat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import android.graphics.Color as GraphicsColor

@Composable
fun LanguageColor(languageColor: String, modifier: Modifier = Modifier) {
  val color = if (languageColor.isEmpty()) {
    MaterialTheme.colors.onSurface
  } else {
    Color(GraphicsColor.parseColor(languageColor))
  }

  Box(
    modifier = modifier.then(
      Modifier
        .clip(CircleShape)
        .preferredSize(10.dp)
        .background(color)
    ),
  )
}

@Composable
fun Language(language: LanguageViewModel, modifier: Modifier = Modifier) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    LanguageColor(languageColor = language.color, modifier = Modifier.padding(end = 6.dp))
    Text(text = language.name, style = MaterialTheme.typography.caption)
  }
}
