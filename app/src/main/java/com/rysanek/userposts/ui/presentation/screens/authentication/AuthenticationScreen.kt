package com.rysanek.userposts.ui.presentation.screens.authentication

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rysanek.userposts.domain.utils.Constants.ATLAS_APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    onSignInClicked: (email: String, password: String) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AuthenticationContent { email, password ->
            onSignInClicked(email, password)
        }
    }
}

@Composable
@Preview
fun AuthenticationScreenPreview() {
    AuthenticationScreen { email, password -> }
}



