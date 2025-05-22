package com.example.primarydetail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import kotlinx.serialization.Serializable

@Serializable
object PostList : NavKey

@Serializable
data class PostDetail(val postId: Long) : NavKey

@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val backStack = rememberNavBackStack(PostList)
            val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
        }
    }
}
