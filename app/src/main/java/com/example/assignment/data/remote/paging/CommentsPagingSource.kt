package com.example.assignment.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment.data.remote.dto.comments.CommentResult
import com.example.assignment.data.remote.service.AssignmentService
import retrofit2.HttpException
import java.io.IOException

class CommentsPagingSource(
    private val assignmentService: AssignmentService,
    private val postId: String,
    private val page: Int
) : PagingSource<Int, CommentResult>() {

    override fun getRefreshKey(state: PagingState<Int, CommentResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentResult> {
        val position = params.key ?: page

        return try {
            val response = assignmentService.getComments(postId, position)
            val results = response.results.orEmpty()
            val totalPages = response.totalPages ?: position

            val prevKey = if (position == page) null else position - 1
            val nextKey = if (position >= totalPages || results.isEmpty()) null else position + 1

            LoadResult.Page(
                data = results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}