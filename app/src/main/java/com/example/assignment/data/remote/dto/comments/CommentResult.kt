package com.example.assignment.data.remote.dto.comments

data class CommentResult(
    val commentId: String? = null,
    val content: String? = null,
    val likes: List<String>? = null,
    val postId: String? = null,
    val timestamp: Long? = null,
    val user: User? = null,
    val userId: String? = null
)