package com.example.mvpapp.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.network.ApiResult
import com.example.mvp_architecture.Model.repo.PostRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


// presentation/post/PostDetailContract.kt
interface PostDetailContract {
    interface View {
        fun showPost(post: Post)
        fun showLoading()
        fun showError(message: String)
    }

    interface Presenter {
        fun loadPost(postId: Int)
        fun onDestroy()
    }
}

class PostDetailPresenter @Inject constructor(
    private val repository: PostRepository,
    private val view: PostDetailContract.View
) : PostDetailContract.Presenter {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private val ioDispatcher = Dispatchers.IO

    override fun loadPost(postId: Int) {
        view.showLoading()
        scope.launch {
            try {
                val post = withContext(ioDispatcher) {
                    repository.getPostById(postId)
                }
                view.showPost(post)
            } catch (e: Exception) {
                view.showError(e.message ?: "Error loading post")
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }
}