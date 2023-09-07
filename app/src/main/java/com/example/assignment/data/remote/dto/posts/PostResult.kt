package com.example.assignment.data.remote.dto.posts

data class PostResult(
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