package com.example.mvp_architecture.view

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mvp_architecture.Model.repo.PostRepository
import com.example.mvp_architecture.view.navigationGraph.AppNavigation
import com.example.mvp_architecture.view.theme.MVP_architectureTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MvpApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: PostRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVP_architectureTheme {
                AppNavigation(repository)
            }
        }
    }
}