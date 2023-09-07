package com.example.assignment.navigation

object Routes {

    object Screen {
        const val COMMENTS_SCREEN = "comments_screen"
        const val HOME_SCREEN = "home_screen"
    }

    object Args {
        const val COMMENTS_POST_ID = "post_id"
        const val COMMENTS_SCREEN = "/{$COMMENTS_POST_ID}"
    }
}