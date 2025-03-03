import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.repo.PostRepository
import com.example.mvpapp.controller.PostDetailContract
import com.example.mvpapp.controller.PostDetailPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId: Int,
    repository: PostRepository,
//    presenterFactory: (PostDetailContract.View) -> PostDetailPresenter
) {
    var post by remember { mutableStateOf<Post?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val view = object : PostDetailContract.View {
        override fun showPost(_post: Post) {
            post = _post
            isLoading = false
        }

        override fun showLoading() {
            isLoading = true
        }

        override fun showError(message: String) {
            error = message
            isLoading = false
        }
    }

    val presenter = remember { PostDetailPresenter(repository, view) }

    DisposableEffect(postId, presenter) {
        presenter.loadPost(postId)
        onDispose { presenter.onDestroy() }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Post Details") }) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text(
                    text = "Error: $error",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                post != null -> Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.9f), // Adjust width if needed
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = post!!.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Post ID: ${post!!.id}",
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "User ID: ${post!!.userId}",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = post!!.body,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
