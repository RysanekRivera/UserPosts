package com.rysanek.userposts.data.apis

import com.rysanek.userposts.data.models.comments.Comments
import com.rysanek.userposts.data.models.posts.Posts
import com.rysanek.userposts.data.models.users.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserPostsApi {

    @GET("/users")
    suspend fun getUsers(): Response<Users>

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: String
    ): Response<Posts>

    @GET("comments")
    suspend fun getComments(
        @Query("postId") userId: String
    ): Response<Comments>

}