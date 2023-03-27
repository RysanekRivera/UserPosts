package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.models.comments.Comment
import com.rysanek.userposts.data.models.comments.Comments
import com.rysanek.userposts.data.repositories.UserPostsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class GetCommentsTest {

    private val repository: UserPostsRepository = mock(UserPostsRepository::class.java)
    private val getComments: GetComments = GetComments(repository)

    @Test
    fun `fetch with successful response returns comments`() = runBlocking {

        val postId = "1"
        val comments = Comments()

        comments.addAll(
            listOf(
                Comment(id = 1, name = "Test comment 1", body = "body 1", email = "email1@email.com", postId = 1),
                Comment(id = 2, name = "Test comment 2", body = "body 2", email = "email2@email.com", postId = 1),
            )
        )

        val expected = flow { emit(Response.success(comments)) }

        `when`(repository.getComments(postId)).thenReturn(expected)

        val result = getComments.fetch(postId)

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns empty list`() = runBlocking {

        val postId = "1"

        val expected = flow<Response<Comments>> { emit(Response.error(401, "Error".toResponseBody())) }

        `when`(repository.getComments(postId)).thenReturn(expected)

        val result = getComments.fetch(postId)

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns correct error response`() = runBlocking {

        val postId = "1"

        val actualErrorResponse = Response.error<Comments>(401, "Error".toResponseBody())

        `when`(repository.getComments(postId)).thenReturn(flow { emit(actualErrorResponse) })

        val result = getComments.fetch(postId)

        result.collectLatest { expectedErrorResponse ->
            assertEquals(actualErrorResponse, expectedErrorResponse)
        }
    }
}