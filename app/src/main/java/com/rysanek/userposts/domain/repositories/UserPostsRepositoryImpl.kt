package com.rysanek.userposts.domain.repositories

import com.rysanek.userposts.data.models.comments.Comments
import com.rysanek.userposts.data.models.posts.Posts
import com.rysanek.userposts.data.models.users.Users
import com.rysanek.userposts.data.apis.UserPostsApi
import com.rysanek.userposts.data.repositories.UserPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class UserPostsRepositoryImpl @Inject constructor(
    private val api: UserPostsApi
): UserPostsRepository {

    override suspend fun getUsers(): Flow<Response<Users>> = flow {emit(api.getUsers()) }

    override suspend fun getPosts(userId:String): Flow<Response<Posts>> = flow { emit(api.getPosts(userId)) }

    override suspend fun getComments(postId: String): Flow<Response<Comments>> = flow { emit(api.getComments(postId)) }

}