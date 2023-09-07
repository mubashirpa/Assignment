package com.example.assignment.presentation.comments.screen.components

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assignment.core.ext.clickable
import com.example.assignment.data.remote.dto.posts.MediaType
import com.example.assignment.data.remote.dto.posts.MultiMediaContent
import com.example.assignment.data.remote.dto.posts.PostType
import com.example.assignment.domain.models.PostResultModel
import com.example.assignment.presentation.components.UserAvatar
import com.example.assignment.presentation.theme.AssignmentTheme
import java.util.Date
import com.example.assignment.R.plurals as Plurals
import com.example.assignment.R.string as Strings

@Composable
fun PostListItem(post: PostResultModel) {
    val timestamp = Date(post.timestamp!!)
    val time = DateUtils.getRelativeTimeSpanString(timestamp.time)

    PostListItemLayout(
        userId = post.userId.orEmpty(),
        username = post.user!!.username!!,
        time = time.toString(),
        type = post.postType!!.name,
        profilePictureUrl = post.user.profilePictureUrl,
        textContent = post.textContent,
        multiMediaContent = post.multiMediaContent.orEmpty(),
        comments = post.comments.orEmpty().size
    )
}

@Composable
private fun PostListItemLayout(
    userId: String,
    username: String,
    time: String,
    type: String,
    profilePictureUrl: String?,
    textContent: String?,
    multiMediaContent: List<MultiMediaContent>,
    comments: Int
) {
    Column {
        ListItem(
            headlineContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = username,
                        modifier = Modifier.weight(1F, fill = false),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = AssistChipDefaults.elevatedAssistChipColors().containerColor,
                        tonalElevation = 1.dp,
                        shadowElevation = 1.dp
                    ) {
                        Text(
                            text = type,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            },
            supportingContent = {
                Text(text = time)
            },
            leadingContent = {
                UserAvatar(id = userId, fullName = username, photoUrl = profilePictureUrl)
            },
            trailingContent = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null)
                }
            }
        )
        if (!textContent.isNullOrEmpty()) {
            Text(
                text = textContent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (multiMediaContent.isNotEmpty()) {
            MultiMediaContentRow(multiMediaContent = multiMediaContent)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pluralStringResource(
                    id = Plurals.number_of_comments,
                    count = comments,
                    comments
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
            SortButton()
        }
    }
}

@Composable
private fun MultiMediaContentRow(multiMediaContent: List<MultiMediaContent>) {
    if (multiMediaContent.size == 1) {
        MultiMediaContentItem(
            multiMediaContent = multiMediaContent.first(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16F / 9F)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                items(multiMediaContent) {
                    MultiMediaContentItem(
                        multiMediaContent = it,
                        modifier = Modifier
                            .width(196.dp)
                            .aspectRatio(4F / 5F)
                    )
                }
            }
        )
    }
}

@Composable
private fun MultiMediaContentItem(
    multiMediaContent: MultiMediaContent,
    modifier: Modifier = Modifier
) {
    when (multiMediaContent.type) {
        MediaType.AUDIO -> {
            Card(modifier = modifier) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(multiMediaContent.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.PlayCircle, contentDescription = null)
                    }
                }
            }
        }

        MediaType.IMAGE -> {
            Card(modifier = modifier) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(multiMediaContent.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        MediaType.VIDEO -> {
            Card(modifier = modifier) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(multiMediaContent.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.PlayCircle, contentDescription = null)
                    }
                }
            }
        }

        null -> {
            Card(modifier = modifier) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = Strings.unable_to_load_media))
                }
            }
        }
    }
}

@Composable
private fun SortButton() {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Row(
            modifier = Modifier.clickable(onClick = { expanded = true }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.UnfoldMore,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(id = Strings.recent),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = Strings.recent)) },
                onClick = { expanded = false }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = Strings.top)) },
                onClick = { expanded = false }
            )
        }
    }
}

@Preview
@Composable
private fun PostListItemPreview() {
    AssignmentTheme {
        Surface {
            PostListItemLayout(
                userId = "123",
                username = "Ayush Agarwal",
                time = "2 hours ago",
                type = PostType.QUESTION.name,
                profilePictureUrl = null,
                textContent = "A yellow face with a frown and closed, downcast eyes, as if aching with sorrow or pain?",
                multiMediaContent = listOf(MultiMediaContent(type = MediaType.IMAGE, url = "")),
                comments = 165
            )
        }
    }
}