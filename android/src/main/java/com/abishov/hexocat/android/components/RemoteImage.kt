package com.abishov.hexocat.android.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@Composable
@OptIn(ExperimentalCoilApi::class)
fun RemoteImage(
    url: Uri,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier
) {
    Image(
        painter = rememberImagePainter(data = url,
            builder = {
                crossfade(true)
            }),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}
