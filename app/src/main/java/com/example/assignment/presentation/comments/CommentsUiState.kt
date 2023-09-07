package com.example.assignment.presentation.comments

import androidx.paging.PagingData
import com.example.assignment.core.utils.Resource
import com.example.assignment.domain.models.CommentResultModel
import com.example.assignment.domain.models.PostResultModel
import kotlinx.coroutines.flow.MutableStateFlow

data class CommentsUiState(
    val commentsPagingData: MutableStateFlow<PagingData<CommentResultModel>> = MutableStateFlow(
        PagingData.empty()
    ),
    val postResource: Resource<PostResultModel> = Resource.Empty()
)