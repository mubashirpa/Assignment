package com.example.assignment.data.mapper

import com.example.assignment.data.remote.dto.posts.PostResult
import com.example.assignment.domain.models.PostResultModel

fun PostResult.toPostResultModel(): PostResultModel {
    return PostResultModel(
        comments,
        likes,
        multiMediaContent,
        postId,
        postType,
        textContent,
        timestamp,
        user,
        userId
    )
}