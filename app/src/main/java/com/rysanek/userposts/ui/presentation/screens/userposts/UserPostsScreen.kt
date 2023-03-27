package com.rysanek.userposts.ui.presentation.screens.userposts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rysanek.userposts.ui.viewmodels.FetchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserPostsScreen(
    modifier: Modifier = Modifier,
    viewModel: FetchViewModel = hiltViewModel()

) {

    val users by viewModel.users.collectAsStateWithLifecycle()
    var showLoadingSpinner by rememberSaveable { mutableStateOf(false) }

    viewModel.responseToNetworkEvents(
        onIdle = { showLoadingSpinner = false },
        onError = { showLoadingSpinner = false },
        onSuccess = { showLoadingSpinner = false },
        onLoading = { showLoadingSpinner = true }
    )

    Scaffold(modifier = modifier) {
        Column {

            UserItems(users)

            Spacer(modifier = Modifier.height(20.dp))

        }

        if (showLoadingSpinner)
            Dialog(onDismissRequest = { }) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(100.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
    }
}