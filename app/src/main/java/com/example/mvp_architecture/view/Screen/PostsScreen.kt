package com.example.mvpapp.ui.screens.posts

import PostPresenter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.repo.PostRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    navController: NavController,
    repository: PostRepository,
//    presenterFactory: (PostContract.View) -> PostPresenter
) {
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val view = object : PostContract.View {
        override fun showPosts(_posts: List<Post>) {
            posts = _posts
            isLoading = false
        }

        override fun showLoading() {
            isLoading = true
        }

        override fun showError(message: String) {
            error = message
            isLoading = false
        }

        override fun navigateToPostDetail(postId: Int) {
            navController.navigate("post/$postId")
        }
    }

    val presenter = remember { PostPresenter(repository , view) }

    DisposableEffect(presenter) {
        presenter.loadPosts()
        onDispose { presenter.onDestroy() }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Posts") }) }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text(
                    text = "Error: $error",
                )

                else -> LazyColumn() {
                    items(posts) { post ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { presenter.onPostClicked(postId = post.id) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = post.body)
                            }
                        }
                    }
                }
            }
        }
    }
}
