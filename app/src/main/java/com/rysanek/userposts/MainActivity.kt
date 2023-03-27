package com.rysanek.userposts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.rysanek.userposts.domain.utils.Constants
import com.rysanek.userposts.ui.presentation.navigation.Screen
import com.rysanek.userposts.ui.presentation.navigation.SetupNavGraph
import com.rysanek.userposts.ui.theme.UserPostsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            UserPostsTheme {
                val navController = rememberNavController()
                val isUserAuthenticated = App.create(Constants.ATLAS_APP_ID).currentUser?.loggedIn == true
                val startDestination = if (isUserAuthenticated) Screen.UserPosts.route else Screen.Authentication.route

                SetupNavGraph(
                    startDestination = startDestination,
                    navController = navController
                )
            }
        }
    }
}


