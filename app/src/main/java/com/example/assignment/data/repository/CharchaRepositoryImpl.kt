package com.example.assignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment.data.remote.dto.comments.CommentResult
import com.example.assignment.data.remote.dto.posts.PostResult
import com.example.assignment.data.remote.paging.CommentsPagingSource
import com.example.assignment.data.remote.paging.PostsPagingSource
import com.example.assignment.data.remote.service.AssignmentService
import com.example.assignment.domain.repository.CharchaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharchaRepositoryImpl @Inject constructor(
    private val assignmentService: AssignmentService
) : CharchaRepository {

    override suspend fun getPosts(page: Int): Flow<PagingData<PostResult>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PostsPagingSource(assignmentService, page)
            }
        ).flow
    }

    override suspend fun getPost(postId: String): PostResult {
        return assignmentService.getPost(postId)
    }

    override suspend fun getComments(postId: String, page: Int): Flow<PagingData<CommentResult>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                CommentsPagingSource(assignmentService, postId, page)
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}