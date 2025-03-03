package com.example.mvp_architecture.Model.module

import PostPresenter
import com.example.mvp_architecture.Model.api.PostApi
import com.example.mvp_architecture.Model.api_service_impl.PostApiImpl
import com.example.mvp_architecture.Model.repo.PostRepository
import com.example.mvp_architecture.Model.repo_impl.PostRepositoryImpl
import com.example.mvpapp.controller.PostDetailContract
import com.example.mvpapp.controller.PostDetailPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): PostApi {
        return PostApiImpl(client)
    }

    @Provides
    @Singleton
    fun providePostRepository(apiService: PostApi): PostRepository {
        return PostRepositoryImpl(apiService)
    }
}