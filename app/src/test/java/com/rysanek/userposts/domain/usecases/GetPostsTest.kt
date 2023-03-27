package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.models.posts.Post
import com.rysanek.userposts.data.models.posts.Posts
import com.rysanek.userposts.data.repositories.UserPostsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class GetPostsTest {
    private val repository: UserPostsRepository = Mockito.mock(UserPostsRepository::class.java)
    private val getPosts: GetPosts = GetPosts(repository)

    @Test
    fun `fetch with successful response returns posts`() = runBlocking {

        val userId = "1"
        val posts = Posts()

        posts.addAll(
            listOf(
                Post(id = 1, title = "Test Post 1", body = "body 1", userId = 1),
                Post(id = 2, title = "Test Post 2", body = "body 2", userId = 1),
            )
        )

        val expected = flow { emit(Response.success(posts)) }

        Mockito.`when`(repository.getPosts(userId)).thenReturn(expected)

        val result = getPosts.fetch(userId)

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns empty list`() = runBlocking {

        val postId = "1"

        val expected = flow<Response<Posts>> { emit(Response.error(401, "Error".toResponseBody())) }

        Mockito.`when`(repository.getPosts(postId)).thenReturn(expected)

        val result = getPosts.fetch(postId)

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns correct error response`() = runBlocking {

        val userId = "1"

        val actualErrorResponse = Response.error<Posts>(401, "Error".toResponseBody())

        Mockito.`when`(repository.getPosts(userId))
            .thenReturn(flow { emit(actualErrorResponse) })

        val result = getPosts.fetch(userId)

        result.collectLatest { expectedErrorResponse ->
            assertEquals(actualErrorResponse, expectedErrorResponse)
        }
    }
}