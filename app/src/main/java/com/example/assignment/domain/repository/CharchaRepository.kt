package com.example.assignment.domain.repository

import androidx.paging.PagingData
import com.example.assignment.data.remote.dto.comments.CommentResult
import com.example.assignment.data.remote.dto.posts.PostResult
import kotlinx.coroutines.flow.Flow

interface CharchaRepository {

    suspend fun getPosts(page: Int = 1): Flow<PagingData<PostResult>>

    suspend fun getPost(postId: String): PostResult

    suspend fun getComments(postId: String, page: Int = 1): Flow<PagingData<CommentResult>>
}