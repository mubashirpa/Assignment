package com.example.assignment.domain.models

import com.example.assignment.data.remote.dto.comments.User

data class CommentResultModel(
    val commentId: String? = null,
    val content: String? = null,
    val likes: List<String>? = null,
    val postId: String? = null,
    val timestamp: Long? = null,
    val user: User? = null,
    val userId: String? = null
)