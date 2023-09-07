package com.example.assignment.data.remote.dto.comments

data class CommentsDto(
    val page: Int? = null,
    val results: List<CommentResult>? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
)