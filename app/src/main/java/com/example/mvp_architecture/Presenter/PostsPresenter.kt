import com.example.mvp_architecture.Model.data_model.Post
import com.example.mvp_architecture.Model.repo.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Assuming existing PostContract for PostsScreen
interface PostContract {
    interface View {
        fun showPosts(posts: List<Post>)
        fun showLoading()
        fun showError(message: String)
        fun navigateToPostDetail(postId: Int) //
    }

    interface Presenter {
        fun onPostClicked(postId: Int)
        fun loadPosts()
        fun onDestroy()
    }
}

// Assuming existing PostPresenter
class PostPresenter(
    private val repository: PostRepository,
    private val view: PostContract.View
) : PostContract.Presenter {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private val ioDispatcher = Dispatchers.IO

    override fun loadPosts() {
        view.showLoading()
        scope.launch {
            try {
                val posts = withContext(ioDispatcher) { repository.getPosts() }
                view.showPosts(posts)
            } catch (e: Exception) {
                view.showError(e.message ?: "Error loading posts")
            }
        }
    }

    override fun onPostClicked(postId: Int) {
        view.navigateToPostDetail(postId) // Delegate navigation to View
    }

    override fun onDestroy() {
        scope.cancel()
    }
}