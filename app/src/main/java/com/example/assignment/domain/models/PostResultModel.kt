package com.example.assignment.domain.models

import com.example.assignment.data.remote.dto.posts.MultiMediaContent
import com.example.assignment.data.remote.dto.posts.PostType
import com.example.assignment.data.remote.dto.posts.User

data class PostResultModel(
    val comments: List<String>? = null,
    val likes: List<String>? = null,
    val multiMediaContent: List<MultiMediaContent>? = null,
    val postId: String? = null,
    val postType: PostType? = null,
    val textContent: String? = null,
    val timestamp: Long? = null,
    val user: User? = null,
    val userId: String? = null
)