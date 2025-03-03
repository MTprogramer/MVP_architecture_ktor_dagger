package com.example.mvp_architecture.Model.api_service_impl

import com.example.mvp_architecture.Model.api.PostApi
import com.example.mvp_architecture.Model.data_model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject


class PostApiImpl @Inject constructor(
    private val client: HttpClient
) : PostApi {
    override suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    override suspend fun getPostById(id: Int): Post {
        return client.get("https://jsonplaceholder.typicode.com/posts/$id").body()
    }
}