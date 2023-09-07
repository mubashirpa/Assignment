package com.example.assignment.navigation

sealed class Screen(val route: String, val args: String? = null) {
    data object CommentsScreen : Screen(Routes.Screen.COMMENTS_SCREEN, Routes.Args.COMMENTS_SCREEN)
    data object HomeScreen : Screen(Routes.Screen.HOME_SCREEN)

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
