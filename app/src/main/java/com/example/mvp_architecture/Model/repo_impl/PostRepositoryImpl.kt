package com.example.mvp_architecture.Model.repo_impl

import com.example.mvp_architecture.Model.api.PostApi
import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.network.ApiResult
import com.example.mvp_architecture.Model.repo.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts()
    }

    override suspend fun getPostById(id: Int): Post {
        return postApi.getPostById(id)
    }
}