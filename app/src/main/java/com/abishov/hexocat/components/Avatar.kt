package com.abishov.hexocat.components

import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

val AvatarCornerSize = 4.dp

@Composable
fun Avatar(url: Uri, modifier: Modifier, cornerRadius: Dp) {
  CoilImage(
    data = url,
    fadeIn = true,
    contentScale = ContentScale.Fit,
    modifier = modifier.then(
      Modifier.clip(RoundedCornerShape(cornerRadius))
    )
  )
}
