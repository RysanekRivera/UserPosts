package com.rysanek.userposts.ui.presentation.navigation

sealed class Screen(val route: String) {

    object Authentication: Screen(route = "authentication_screen")
    object UserPosts: Screen(route = "user_posts_screen")

}
