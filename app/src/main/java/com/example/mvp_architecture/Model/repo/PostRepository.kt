package com.example.mvp_architecture.Model.repo

import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getPostById(id: Int): Post
}