package com.example.mvp_architecture.view.navigationGraph

import PostDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvp_architecture.Model.repo.PostRepository
import com.example.mvp_architecture.view.Screen.HomeScreen
import com.example.mvpapp.ui.screens.posts.PostsScreen

@Composable
fun AppNavigation(repository: PostRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("posts") { PostsScreen(navController , repository) }
        composable("post/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: 1
            PostDetailScreen(postId , repository)
        }
    }
}