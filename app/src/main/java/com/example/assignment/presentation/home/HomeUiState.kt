package com.example.assignment.presentation.home

import androidx.paging.PagingData
import com.example.assignment.domain.models.PostResultModel
import kotlinx.coroutines.flow.MutableStateFlow

data class HomeUiState(
    val postsPagingData: MutableStateFlow<PagingData<PostResultModel>> = MutableStateFlow(PagingData.empty()),
)