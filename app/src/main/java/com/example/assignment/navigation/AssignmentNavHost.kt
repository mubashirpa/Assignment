package com.example.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment.presentation.comments.CommentsViewModel
import com.example.assignment.presentation.comments.screen.CommentsScreen
import com.example.assignment.presentation.home.HomeViewModel
import com.example.assignment.presentation.home.screen.HomeScreen

@Composable
fun AssignmentNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                uiState = viewModel.uiState,
                navigateToComments = {
                    navController.navigate(Screen.CommentsScreen.withArgs(it))
                }
            )
        }
        composable(route = "${Screen.CommentsScreen.route}${Screen.CommentsScreen.args}") {
            val viewModel: CommentsViewModel = hiltViewModel()
            CommentsScreen(
                uiState = viewModel.uiState,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}