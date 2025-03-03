package com.example.mvp_architecture.Model.api

import com.example.mvp_architecture.Model.data_model.Post

interface PostApi {
    suspend fun getPosts(): List<Post>
    suspend fun getPostById(id: Int): Post
}