package com.example.assignment.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.assignment.data.mapper.toCommentResultModel
import com.example.assignment.domain.models.CommentResultModel
import com.example.assignment.domain.repository.CharchaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val charchaRepository: CharchaRepository
) {
    suspend operator fun invoke(
        postId: String,
        page: Int = 1
    ): Flow<PagingData<CommentResultModel>> {
        return charchaRepository.getComments(postId, page).map { pagingData ->
            pagingData.map {
                it.toCommentResultModel()
            }
        }
    }
}