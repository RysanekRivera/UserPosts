package com.rysanek.userposts.ui.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rysanek.userposts.ui.presentation.screens.authentication.AuthenticationContent
import com.rysanek.userposts.ui.presentation.screens.authentication.AuthenticationViewModel
import com.rysanek.userposts.ui.presentation.screens.userposts.UserPostsScreen
import com.rysanek.userposts.ui.viewmodels.FetchViewModel

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationRoute(navController)
        userPostsRoute(navController)
    }

}

fun NavGraphBuilder.authenticationRoute(navController: NavController) {
    composable(route = Screen.Authentication.route) {

        val viewModel: AuthenticationViewModel = hiltViewModel()
        val showLoadingSpinner by viewModel.loadingState

        AuthenticationContent { email, password ->
            viewModel.checkIfUserIsCredentialed(
                email = email,
                password = password,
                onSuccess = {
                    navController.navigate(Screen.UserPosts.route) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                },
                onInvalidCredentials = {

                },
                onError = {

                }
            )

        }

        if (showLoadingSpinner)
            Dialog(onDismissRequest = {}) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(100.dp)
                ) {
                    CircularProgressIndicator()
                }
            }


    }
}

fun NavGraphBuilder.userPostsRoute(navController: NavController) {
    composable(route = Screen.UserPosts.route) {
        val authViewModel: AuthenticationViewModel = hiltViewModel()
        val fetchViewModel: FetchViewModel = hiltViewModel()
        var shouldFetch by rememberSaveable { mutableStateOf(true) }

        Column {
            UserPostsScreen(modifier = Modifier.weight(.92f))

            Button(
                modifier = Modifier
                    .weight(.08f)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .fillMaxHeight(),
                content = { Text(text = "Sign Out", fontSize = TextUnit(16f, TextUnitType.Sp), fontWeight = FontWeight.Bold) },
                onClick = {

                    authViewModel.logOut(
                        onLogOutSuccess = {
                            navController.navigate(Screen.Authentication.route) { popUpTo(Screen.UserPosts.route) { inclusive = true } }
                        },
                        onLogOutError = {

                        }
                    )

                }
            )
        }

        if (shouldFetch){
            fetchViewModel.fetchUsers()
            shouldFetch = false
        }

    }
}

