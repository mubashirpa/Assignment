package com.example.assignment.data.remote.dto.posts

data class PostsDto(
    val page: Int? = null,
    val results: List<PostResult>? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
)