package com.example.assignment.presentation.comments.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.assignment.core.utils.Resource
import com.example.assignment.presentation.comments.CommentsUiState
import com.example.assignment.presentation.comments.screen.components.CommentListItem
import com.example.assignment.presentation.comments.screen.components.PostListItem
import com.example.assignment.presentation.components.Axis
import com.example.assignment.presentation.components.BackButton
import com.example.assignment.presentation.components.ErrorScreen
import com.example.assignment.presentation.components.LoadingIndicator
import retrofit2.HttpException
import java.io.IOException
import com.example.assignment.R.string as Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    uiState: CommentsUiState,
    onBackPressed: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val postResource = uiState.postResource

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = Strings.title_comments_screen),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                navigationIcon = { BackButton(onClick = onBackPressed) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        if (postResource is Resource.Success) {
            val commentsPagingItems = uiState.commentsPagingData.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = innerPadding,
                content = {
                    val post = postResource.data
                    if (post != null) {
                        item {
                            PostListItem(post = post)
                        }
                    }

                    when (val refreshState = commentsPagingItems.loadState.refresh) {
                        is LoadState.Error -> {
                            item {
                                val message = when (refreshState.error) {
                                    is IOException -> {
                                        stringResource(id = Strings.error_no_internet)
                                    }

                                    is HttpException -> {
                                        stringResource(id = Strings.error_unexpected)
                                    }

                                    else -> {
                                        stringResource(id = Strings.error_unknown)
                                    }
                                }
                                ErrorScreen(
                                    onRetry = {
                                        commentsPagingItems.refresh()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    message = message
                                )
                            }
                        }

                        LoadState.Loading -> {
                            item {
                                LoadingIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                )
                            }
                        }

                        is LoadState.NotLoading -> {
                            items(
                                count = commentsPagingItems.itemCount,
                                key = commentsPagingItems.itemKey(),
                                contentType = commentsPagingItems.itemContentType()
                            ) { index ->
                                val comment = commentsPagingItems[index]
                                if (comment != null) {
                                    CommentListItem(comment = comment)
                                }

                                if (index < commentsPagingItems.itemCount - 1) {
                                    HorizontalDivider(thickness = Dp.Hairline)
                                }
                            }

                            when (val appendState = commentsPagingItems.loadState.append) {
                                is LoadState.Error -> {
                                    item {
                                        val message = when (appendState.error) {
                                            is IOException -> {
                                                stringResource(id = Strings.error_no_internet)
                                            }

                                            is HttpException -> {
                                                stringResource(id = Strings.error_unexpected)
                                            }

                                            else -> {
                                                stringResource(id = Strings.error_unknown)
                                            }
                                        }
                                        ErrorScreen(
                                            onRetry = {
                                                commentsPagingItems.retry()
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 12.dp),
                                            direction = Axis.Horizontal,
                                            message = message
                                        )
                                    }
                                }

                                LoadState.Loading -> {
                                    item {
                                        LoadingIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 12.dp),
                                        )
                                    }
                                }

                                is LoadState.NotLoading -> {}
                            }
                        }
                    }
                }
            )
        }
    }
}