package com.abishov.hexocat.android.components

import android.net.Uri
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val AvatarCornerSize = 4.dp

@Composable
fun Avatar(url: Uri, modifier: Modifier, cornerRadius: Dp) {
    RemoteImage(
        url = url,
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = modifier.then(
            Modifier.clip(RoundedCornerShape(cornerRadius))
        )
    )
}
