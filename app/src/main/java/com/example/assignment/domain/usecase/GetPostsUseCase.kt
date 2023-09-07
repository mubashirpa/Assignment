package com.example.assignment.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.assignment.data.mapper.toPostResultModel
import com.example.assignment.domain.models.PostResultModel
import com.example.assignment.domain.repository.CharchaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val charchaRepository: CharchaRepository
) {
    suspend operator fun invoke(page: Int = 1): Flow<PagingData<PostResultModel>> {
        return charchaRepository.getPosts(page).map { pagingData ->
            pagingData.map {
                it.toPostResultModel()
            }
        }
    }
}