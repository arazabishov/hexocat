package com.abishov.hexocat.android.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.google.accompanist.flowlayout.FlowRow

data class OwnerViewModel(
    val avatarUrl: Uri,
    val login: String,
    val id: String,
)

data class RepositoryViewModel(
    val name: String,
    val description: String?,
    val bannerUrl: Uri?,
    val stars: Int,
    val url: Uri,
    val owner: OwnerViewModel,
    val primaryLanguage: LanguageViewModel?,
    val topics: List<TopicViewModel>?,
    val mentionableUsers: MentionableUsersViewModel?
)

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
            url = repository.owner.avatarUrl,
            modifier = Modifier.size(40.dp),
            cornerRadius = AvatarCornerSize
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Name(name = repository.name)
            Author(username = repository.owner.login)
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
private fun Banner(url: Uri) {
    RemoteImage(
        url = url,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(194.dp)
    )
}

@Composable
private fun StarButton(stars: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier.then(Modifier.fillMaxWidth())) {
        OutlinedButton(onClick = { /* TODO */ }, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                modifier = Modifier
                    .padding(end = ButtonDefaults.IconSpacing)
                    .size(ButtonDefaults.IconSize),
                contentDescription = "",
                imageVector = Icons.Outlined.StarOutline,
                tint = MaterialTheme.colors.secondary,
            )
            Text(text = stars.toString(), color = MaterialTheme.colors.secondary)
        }
    }
}

@Composable
private fun Tags(
    primaryLanguage: LanguageViewModel?,
    topics: List<TopicViewModel>?,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        FlowRow(mainAxisSpacing = 16.dp, crossAxisSpacing = 8.dp) {
            primaryLanguage?.let { Language(it) }
            topics?.forEach { Topic(it) }
        }
    }
}

@Composable
private fun RepositoryCard(
    repository: RepositoryViewModel,
    onRepositoryClick: (repository: RepositoryViewModel) -> Unit,
    content: @Composable ColumnScope.() -> Unit
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
                content()
            }
        }
    }
}

@Composable
fun Repository(
    repository: RepositoryViewModel,
    onRepositoryClick: (repository: RepositoryViewModel) -> Unit
) {
    RepositoryCard(repository, onRepositoryClick) {
        if (repository.bannerUrl != null) {
            Banner(url = repository.bannerUrl)
        }

        Header(repository)

        if (repository.description != null && repository.description.isNotEmpty()) {
            Description(description = repository.description)
        }

        Tags(
            primaryLanguage = repository.primaryLanguage,
            topics = repository.topics,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (repository.mentionableUsers != null) {
            Contributors(
                owner = repository.owner,
                users = repository.mentionableUsers,
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

@Composable
fun Repositories(
    repos: List<RepositoryViewModel>,
    onRepositoryClick: (RepositoryViewModel) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(repos.size, { index -> repos[index].url }) { index ->
            Repository(repository = repos[index], onRepositoryClick = onRepositoryClick)
        }
    }
}
