package com.rysanek.userposts.data.repositories

import com.rysanek.userposts.data.models.comments.Comments
import com.rysanek.userposts.data.models.posts.Posts
import com.rysanek.userposts.data.models.users.Users
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserPostsRepository {

    suspend fun getUsers(): Flow<Response<Users>>

    suspend fun getPosts(userId: String): Flow<Response<Posts>>

    suspend fun getComments(postId: String): Flow<Response<Comments>>

}