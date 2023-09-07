package com.example.assignment.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.assignment.navigation.AssignmentNavHost

@Composable
fun AssignmentApp() {
    val navController = rememberNavController()

    AssignmentNavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}