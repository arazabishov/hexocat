package com.abishov.hexocat.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage

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
    Avatar(
      url = repository.avatarUrl,
      modifier = Modifier.preferredSize(40.dp),
      cornerRadius = AvatarCornerSize
    )
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
fun StarButton(stars: String, modifier: Modifier = Modifier) {
  Box(modifier = modifier.then(Modifier.fillMaxWidth())) {
    OutlinedButton(onClick = { /* TODO */ }, modifier = Modifier.align(Alignment.CenterEnd)) {
      Icon(
        modifier = Modifier
          .padding(end = ButtonDefaults.IconSpacing)
          .preferredSize(ButtonDefaults.IconSize),
        imageVector = Icons.Outlined.StarOutline,
        tint = MaterialTheme.colors.secondary,
      )
      Text(text = stars, color = MaterialTheme.colors.secondary)
    }
  }
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
          languages = repository.languages,
          topics = repository.topics,
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        val topContributors = repository.contributors.contributors
        val isOwnerTheOnlyContributor = topContributors.size == 1 &&
            topContributors.first().id == repository.ownerId

        if (!isOwnerTheOnlyContributor) {
          Contributors(
            users = repository.contributors,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }

        StarButton(
          repository.stars,
          modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
        )
      }
    }
  }
}
