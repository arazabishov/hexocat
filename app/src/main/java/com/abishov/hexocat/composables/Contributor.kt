package com.abishov.hexocat.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val contributorAvatarSize = 28.dp

@Composable
fun Contributor(contributor: ContributorViewModel) {
  Avatar(
    url = contributor.avatarUrl,
    Modifier.preferredSize(contributorAvatarSize),
    AvatarCornerSize
  )
}

@Composable
fun ContributorOverflow(contributorsOverflow: Int) {

  @Composable
  fun ContributorOutline(
    color: Color,
    avatarCornerSize: Dp,
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit
  ) {
    val contributorCornerRadius = with(AmbientDensity.current) {
      val avatarSizePx = contributorAvatarSize.toPx()
      val avatarSize = Size(avatarSizePx, avatarSizePx)
      val cornerSize = CornerSize(avatarCornerSize).toPx(avatarSize, this)

      CornerRadius(cornerSize)
    }

    Box(
      modifier = modifier.then(
        Modifier.preferredSize(contributorAvatarSize)
      ), contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = modifier, onDraw = {
        drawRoundRect(
          color = color,
          style = Stroke(width = Dp.Hairline.value),
          cornerRadius = contributorCornerRadius
        )
      })
      content()
    }
  }

  ContributorOutline(
    MaterialTheme.colors.onSurface,
    AvatarCornerSize,
    Modifier.preferredSize(contributorAvatarSize)
  ) {
    val overflow = if (contributorsOverflow > 99)
      99 else contributorsOverflow

    Text(
      text = "+${overflow}",
      style = MaterialTheme.typography.caption,
      fontWeight = FontWeight.Light
    )
  }
}
