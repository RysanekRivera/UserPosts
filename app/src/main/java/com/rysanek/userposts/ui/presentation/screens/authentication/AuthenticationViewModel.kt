package com.rysanek.userposts.ui.presentation.screens.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.userposts.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.exceptions.ServiceException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(): ViewModel() {

    private val mongoApp = App.create(Constants.ATLAS_APP_ID)
    var loadingState = mutableStateOf(false)
        private set

    fun checkIfUserIsCredentialed(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onInvalidCredentials:(message: String) -> Unit,
        onError: (message: String) -> Unit
    ){
        val isUserCredentialed = flow { emit(mongoApp.login(Credentials.emailPassword(email, password)).loggedIn) }

        viewModelScope.launch {
            loadingState.value = true
            isUserCredentialed
                .catch {e ->
                    when(e){
                        is InvalidCredentialsException -> onInvalidCredentials(e.message ?: "Invalid Credentials")
                        else -> onError(e.message.toString())
                    }
                }
                .onCompletion {
                    loadingState.value = false
                }
                .collect { credentialed ->
                    if (credentialed) {
                        onSuccess()
                    } else {
                       onInvalidCredentials("Invalid Credentials")
                    }
                }
        }
    }

    fun logOut(
        onLogOutSuccess: () -> Unit,
        onLogOutError: (message: String) -> Unit
    ){
        val userLogOut = flow { emit(mongoApp.currentUser?.logOut())  }
        viewModelScope.launch {
            userLogOut
                .catch {e ->
                    when(e){
                        is ServiceException -> onLogOutError(e.message ?: "Problem Communicating with Server")
                        else -> onLogOutError(e.message ?: "Unknown Error")
                    }
                }
                .collect{
                    onLogOutSuccess()
                }
        }

    }

}