package com.example.assignment.presentation.home.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.assignment.domain.models.PostResultModel
import com.example.assignment.presentation.components.Axis
import com.example.assignment.presentation.components.ComingSoon
import com.example.assignment.presentation.components.ErrorScreen
import com.example.assignment.presentation.components.LoadingIndicator
import com.example.assignment.presentation.home.HomeScreenPages
import com.example.assignment.presentation.home.HomeUiState
import com.example.assignment.presentation.home.screen.components.HomeListItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.assignment.R.string as Strings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    navigateToComments: (postId: String) -> Unit
) {
    val pages = HomeScreenPages.values()
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                indicator = @Composable { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        val width by animateDpAsState(
                            targetValue = tabPositions[pagerState.currentPage].contentWidth,
                            label = ""
                        )
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            width = width
                        )
                    }
                }
            ) {
                pages.forEachIndexed { index, page ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(text = stringResource(id = page.titleResId)) },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { index ->
                when (pages[index]) {
                    HomeScreenPages.Charcha -> {
                        CharchaContent(
                            postsPagingItems = uiState.postsPagingData.collectAsLazyPagingItems(),
                            navigateToComments = navigateToComments
                        )
                    }

                    else -> {
                        ComingSoon(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
private fun CharchaContent(
    postsPagingItems: LazyPagingItems<PostResultModel>,
    navigateToComments: (postId: String) -> Unit
) {
    when (val refreshState = postsPagingItems.loadState.refresh) {
        is LoadState.Error -> {
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
                    postsPagingItems.refresh()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                message = message
            )
        }

        LoadState.Loading -> {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }

        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(
                        count = postsPagingItems.itemCount,
                        key = postsPagingItems.itemKey(),
                        contentType = postsPagingItems.itemContentType()
                    ) { index ->
                        val post = postsPagingItems[index]
                        if (post != null) {
                            HomeListItem(
                                post = post,
                                onCommentClick = navigateToComments
                            )
                        }

                        if (index < postsPagingItems.itemCount - 1) {
                            HorizontalDivider(thickness = Dp.Hairline)
                        }
                    }

                    when (val appendState = postsPagingItems.loadState.append) {
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
                                        postsPagingItems.retry()
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
            )
        }
    }
}