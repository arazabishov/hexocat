package com.abishov.hexocat.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
private fun Avatar(url: String) {
  CoilImage(
    data = url,
    fadeIn = true,
    contentScale = ContentScale.Fit,
    modifier = Modifier
      .preferredSize(40.dp)
      .clip(RoundedCornerShape(4.dp))
  )
}

@Composable
private fun Name(name: String) {
  Text(
    text = name,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = MaterialTheme.typography.subtitle1,
  )
}

@Composable
private fun Author(username: String) {
  Text(
    text = username,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    style = MaterialTheme.typography.subtitle2
  )
}

@Composable
private fun Header(repository: RepositoryViewModel) {
  Row(
    modifier = Modifier.padding(16.dp)
  ) {
    Avatar(url = repository.avatarUrl)
    Column(modifier = Modifier.padding(start = 16.dp)) {
      Name(name = repository.name)
      Author(username = repository.login)
    }
  }
}

@Composable
private fun Description(description: String) {
  Text(
    text = description,
    style = MaterialTheme.typography.body2,
    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    overflow = TextOverflow.Ellipsis,
    maxLines = 3,
  )
}

@Composable
private fun Banner(url: String) {
  CoilImage(
    data = url,
    fadeIn = true,
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxWidth()
      .height(194.dp)
  )
}

@Composable
@OptIn(ExperimentalLayout::class)
fun RepositoryItem(
  repository: RepositoryViewModel,
  onRepositoryClick: (repository: RepositoryViewModel) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 4.dp),
  ) {
    val cardBorderStroke = BorderStroke(
      1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    Card(
      elevation = 0.dp,
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { onRepositoryClick(repository) }),
      shape = CutCornerShape(4.dp),
      border = cardBorderStroke
    ) {
      Column {
        if (repository.usesBannerUrl) {
          Banner(url = repository.bannerUrl)
        }

        Header(repository)

        if (repository.description.isNotEmpty()) {
          Description(description = repository.description)
        }

        Tags(
          repository.stars,
          languages = repository.languages,
          topics = repository.topics,
          modifier = Modifier.padding(
            start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp
          )
        )
      }
    }
  }
}
