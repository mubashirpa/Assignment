package com.example.assignment.presentation.home.screen.components

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertComment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assignment.core.Constants
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
fun HomeListItem(
    post: PostResultModel,
    onCommentClick: (postId: String) -> Unit
) {
    val postId = post.postId
    val timestamp = Date(post.timestamp!!)
    val time = DateUtils.getRelativeTimeSpanString(timestamp.time)
    val myUserId = Constants.USER_ID
    val likes: List<String> = post.likes.orEmpty()

    HomeListItemLayout(
        userId = post.userId.orEmpty(),
        username = post.user!!.username!!,
        time = time.toString(),
        type = post.postType!!.name,
        profilePictureUrl = post.user.profilePictureUrl,
        textContent = post.textContent,
        multiMediaContent = post.multiMediaContent.orEmpty(),
        likes = likes.size,
        isLiked = likes.contains(myUserId),
        comments = post.comments.orEmpty().size,
        onCommentClick = {
            if (postId != null) {
                onCommentClick(postId)
            }
        }
    )
}

@Composable
private fun HomeListItemLayout(
    userId: String,
    username: String,
    time: String,
    type: String,
    profilePictureUrl: String?,
    textContent: String?,
    multiMediaContent: List<MultiMediaContent>,
    likes: Int,
    isLiked: Boolean,
    comments: Int,
    onCommentClick: () -> Unit
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
        Row(modifier = Modifier.fillMaxWidth()) {
            HomeListItemButton(
                text = pluralStringResource(id = Plurals.number_of_likes, count = likes, likes),
                icon = if (isLiked) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                modifier = Modifier.weight(1F),
                iconTint = if (isLiked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                onClick = { /*TODO*/ }
            )
            HomeListItemButton(
                text = pluralStringResource(
                    id = Plurals.number_of_comments,
                    count = comments,
                    comments
                ),
                icon = Icons.AutoMirrored.Outlined.InsertComment,
                modifier = Modifier.weight(1F),
                onClick = onCommentClick
            )
            HomeListItemButton(
                text = "Share",
                icon = Icons.Outlined.Share,
                modifier = Modifier.weight(1F),
                onClick = { /*TODO*/ }
            )
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
private fun HomeListItemButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun HomeListItemPreview() {
    AssignmentTheme {
        Surface {
            HomeListItemLayout(
                userId = "123",
                username = "Ayush Agarwal",
                time = "2 hours ago",
                type = PostType.MARKETING.name,
                profilePictureUrl = null,
                textContent = "Just landed on urvar",
                multiMediaContent = listOf(MultiMediaContent(type = MediaType.IMAGE, url = "")),
                likes = 121,
                isLiked = true,
                comments = 97,
                onCommentClick = {}
            )
        }
    }
}