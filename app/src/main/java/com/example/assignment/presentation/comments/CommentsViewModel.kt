package com.example.assignment.presentation.comments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.assignment.domain.usecase.GetCommentsUseCase
import com.example.assignment.domain.usecase.GetPostUseCase
import com.example.assignment.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostUseCase: GetPostUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(CommentsUiState())
        private set

    private val postId: String = checkNotNull(savedStateHandle[Routes.Args.COMMENTS_POST_ID])

    init {
        getPost()
        getComments()
    }

    private fun getPost() {
        getPostUseCase(postId).onEach { resource ->
            uiState = uiState.copy(postResource = resource)
        }.launchIn(viewModelScope)
    }

    private fun getComments() {
        viewModelScope.launch {
            getCommentsUseCase(postId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    uiState.commentsPagingData.value = it
                }
        }
    }
}