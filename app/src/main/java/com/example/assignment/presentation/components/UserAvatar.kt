package com.example.assignment.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.assignment.core.ext.toHslColor

@Composable
fun UserAvatar(
    id: String,
    fullName: String,
    photoUri: Uri?,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    shape: Shape = CircleShape
) {
    UserAvatar(
        id = id,
        fullName = fullName,
        photoUrl = photoUri?.toString(),
        modifier = modifier,
        size = size,
        shape = shape
    )
}

@Composable
fun UserAvatar(
    id: String,
    fullName: String,
    photoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    shape: Shape = CircleShape
) {
    val name = fullName.split(" ")
    val firstName = name[0]
    val lastName = if (name.size > 1) name[1] else ""
    val avatar: @Composable () -> Unit = {
        TextAvatar(
            id = id,
            firstName = firstName,
            lastName = lastName,
            modifier = modifier,
            size = size,
            shape = shape
        )
    }

    if (photoUrl.isNullOrBlank()) {
        avatar()
    } else {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(shape)
                .then(modifier),
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }

                is AsyncImagePainter.State.Error -> {
                    avatar()
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}

@Composable
private fun TextAvatar(
    id: String,
    firstName: String,
    lastName: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    shape: Shape = CircleShape
) {
    val color = remember(id, firstName, lastName) {
        val name = listOf(firstName, lastName)
            .joinToString(separator = "")
            .uppercase()
        Color("$id / $name".toHslColor())
    }
    val initials = (firstName.take(1) + lastName.take(1)).uppercase()

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}