package com.rysanek.userposts.domain.usecases

import com.rysanek.userposts.data.repositories.UserPostsRepository
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: UserPostsRepository
) {

    suspend fun fetch(userId: String) = repository.getPosts(userId)

}