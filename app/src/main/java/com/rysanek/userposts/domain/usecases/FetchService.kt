package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.models.posts.Post
import com.rysanek.userposts.data.models.users.User
import com.rysanek.userposts.domain.eventhandlers.NetworkEventHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FetchService @Inject constructor(
    private val getUsers: GetUsers,
    private val getPosts: GetPosts,
    private val getComments: GetComments,
    val eventHandler: NetworkEventHandler
) {

    suspend fun fetchData() = getUsers.fetch()
            .map { response ->
                if (response.isSuccessful && response.body() != null) response.body()!!
                else emptyList()
            }


    suspend fun getPosts(user: User) =
        getPosts.fetch(user.id.toString())
            .map { response ->
                if (response.isSuccessful && response.body() != null) response.body()!!
                else emptyList()
            }


    suspend fun getComments(post: Post) = getComments.fetch(post.id.toString())
        .map { response ->
            if (response.isSuccessful && response.body() != null) response.body()!!
            else emptyList()
        }


}