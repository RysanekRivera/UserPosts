package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.repositories.UserPostsRepository
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val repository: UserPostsRepository
) {

    suspend fun fetch() = repository.getUsers()

}