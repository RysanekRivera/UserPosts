package com.rysanek.userposts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.userposts.data.models.comments.Comment
import com.rysanek.userposts.data.models.posts.Post
import com.rysanek.userposts.data.models.users.User
import com.rysanek.userposts.domain.repositories.RealmDb
import com.rysanek.userposts.domain.usecases.FetchService
import com.rysanek.userposts.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchService: FetchService
) : ViewModel() {

    var users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
        private set

    fun fetchUsers() = viewModelScope.launch {
        fetchService.fetchData()
            .onStart { fetchService.eventHandler.onLoading() }
            .catch { fetchService.eventHandler.onError(it.message) }
            .onCompletion { fetchService.eventHandler.onIdle() }
            .collectLatest { usersList -> users.value = usersList }
    }

    fun fetchPosts(
        user: User,
        onLoading: () -> Unit,
        onPostReceived: (List<Post>) -> Unit,
        onError: (message: String?) -> Unit,
        onCompletion: () -> Unit
    ) = viewModelScope.launch {

        fetchService.getPosts(user)
            .onStart { onLoading() }
            .catch { onError(it.message) }
            .onCompletion { onCompletion() }
            .collectLatest { posts -> onPostReceived(posts) }
    }

    fun fetchComments(
        post: Post,
        onLoading: () -> Unit,
        onCommentsReceived: (List<Comment>) -> Unit,
        onError: (message: String?) -> Unit,
        onCompletion:() -> Unit
    ) = viewModelScope.launch {
        fetchService.getComments(post)
            .onStart { onLoading() }
            .catch { onError(it.message) }
            .onCompletion { onCompletion() }
            .collectLatest { comments -> onCommentsReceived(comments) }
    }

    fun responseToNetworkEvents(
        onSuccess: () -> Unit = {},
        onError: (message: String?) -> Unit = {},
        onLoading: () -> Unit = {},
        onIdle: () -> Unit = {}
    ) {
        fetchService.eventHandler.setOnSuccess(onSuccess)
        fetchService.eventHandler.setOnError(onError)
        fetchService.eventHandler.setOnLoading(onLoading)
        fetchService.eventHandler.setOnIdle(onIdle)
    }

}