package com.example.assignment.presentation.comments.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment.core.Constants
import com.example.assignment.core.ext.clickable
import com.example.assignment.domain.models.CommentResultModel
import com.example.assignment.presentation.components.UserAvatar
import com.example.assignment.presentation.theme.AssignmentTheme
import com.example.assignment.R.string as Strings

@Composable
fun CommentListItem(comment: CommentResultModel) {
    val myUserId = Constants.USER_ID

    CommentListItemLayout(
        userId = comment.userId.orEmpty(),
        username = comment.user!!.username!!,
        profilePictureUrl = comment.user.profilePictureUrl,
        textContent = comment.content,
        isLiked = comment.likes?.contains(myUserId) == true
    )
}

@Composable
private fun CommentListItemLayout(
    userId: String,
    username: String,
    profilePictureUrl: String?,
    textContent: String?,
    isLiked: Boolean
) {
    Column {
        ListItem(
            headlineContent = {
                Text(
                    text = username,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            supportingContent = {
                Text(text = stringResource(id = Strings.public_))
            },
            leadingContent = {
                UserAvatar(id = userId, fullName = username, photoUrl = profilePictureUrl)
            },
            trailingContent = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
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
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isLiked) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isLiked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = Strings.like),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun CommentListItemPreview() {
    AssignmentTheme {
        Surface {
            CommentListItemLayout(
                userId = "123",
                username = "Ayush Agarwal",
                profilePictureUrl = null,
                textContent = "A yellow face with a frown and closed, downcast eyes, as if aching with sorrow or pain",
                isLiked = true
            )
        }
    }
}