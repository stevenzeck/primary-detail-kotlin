package com.example.primarydetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.primarydetail.ui.postdetail.PostDetailScreen
import com.example.primarydetail.ui.postlist.PostListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
object PostList : NavKey

@Serializable
data class PostDetail(val postId: Long) : NavKey

@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "App")
                        },
                    )
                },
            ) { padding ->
                val temp = padding
                val backStack = rememberNavBackStack(PostList)
                val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

                NavDisplay(
                    backStack = backStack,
                    onBack = { keysToRemove -> repeat(keysToRemove) { backStack.removeLastOrNull() } },
                    sceneStrategy = listDetailStrategy,
                    entryProvider = entryProvider {
                        entry<PostList>(
                            metadata = ListDetailSceneStrategy.listPane(
                                detailPlaceholder = {
                                    Text("Select something")
                                }
                            )
                        ) {
                            PostListScreen(onPostSelected = { postId ->
                                backStack.add(PostDetail(postId = postId))
                            })
                        }
                        entry<PostDetail>(
                            metadata = ListDetailSceneStrategy.detailPane()
                        ) { post ->
                            PostDetailScreen()
                        }
                    }
                )
            }
        }
    }
}
