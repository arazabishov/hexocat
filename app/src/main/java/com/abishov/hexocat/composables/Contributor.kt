package com.abishov.hexocat.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

private val CIRCLE_SIZE = 28.dp

@Composable
fun Contributor(contributor: ContributorViewModel) {
  CoilImage(
    fadeIn = true,
    data = contributor.avatarUrl,
    contentScale = ContentScale.Fit,
    modifier = Modifier
      .preferredSize(CIRCLE_SIZE)
      .clip(CircleShape)
  )
}

@Composable
fun ContributorOverflow(contributorsOverflow: Int) {

  @Composable
  fun Circle(color: Color, modifier: Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
      modifier = Modifier.preferredSize(CIRCLE_SIZE),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = modifier, onDraw = {
        drawCircle(color = color, style = Stroke(width = 1.dp.value))
      })
      content()
    }
  }

  Circle(
    MaterialTheme.colors.onSurface,
    Modifier.preferredSize(CIRCLE_SIZE)
  ) {
    val overflow = if (contributorsOverflow > 99)
      "99+" else contributorsOverflow.toString()
    Text(
      text = overflow,
      style = MaterialTheme.typography.caption,
      fontWeight = FontWeight.Light
    )
  }
}
