package com.example.assignment.domain.models

import java.util.Date

data class User(
    val createdAt: Date? = null,
    val email: String? = null,
    val friends: List<String>? = null,
    val posts: List<String>? = null,
    val profilePictureUrl: String? = null,
    val userId: String? = null,
    val username: String? = null
)