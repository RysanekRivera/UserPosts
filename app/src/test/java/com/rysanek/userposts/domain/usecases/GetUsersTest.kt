package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.models.users.User
import com.rysanek.userposts.data.models.users.Users
import com.rysanek.userposts.data.repositories.UserPostsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class GetUsersTest {
    private val repository: UserPostsRepository = Mockito.mock(UserPostsRepository::class.java)
    private val getUsers: GetUsers = GetUsers(repository)

    @Test
    fun `fetch with successful response returns users`() = runBlocking {

        val users = Users()

        users.addAll(
            listOf(
                User(id = 1, name = "User 1", email = "email1@email.com"),
                User(id = 1, name = "User 2", email = "email2@email.com"),
            )
        )

        val expected = flow { emit(Response.success(users)) }

        Mockito.`when`(repository.getUsers()).thenReturn(expected)

        val result = getUsers.fetch()

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns empty list`() = runBlocking {

        val expected = flow<Response<Users>> { emit(Response.error(401, "Error".toResponseBody())) }

        Mockito.`when`(repository.getUsers()).thenReturn(expected)

        val result = getUsers.fetch()

        assertEquals(expected, result)

    }

    @Test
    fun `fetch with error response returns correct error response`() = runBlocking {

        val actualErrorResponse = Response.error<Users>(401, "Error".toResponseBody())

        Mockito.`when`(repository.getUsers())
            .thenReturn(flow { emit(actualErrorResponse) })

        val result = getUsers.fetch()

        result.collectLatest { expectedErrorResponse ->
            assertEquals(actualErrorResponse, expectedErrorResponse)
        }
    }
}