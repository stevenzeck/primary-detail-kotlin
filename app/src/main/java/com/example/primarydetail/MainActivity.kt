package com.example.primarydetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.primarydetail.ui.postdetail.PostDetailScreen
import com.example.primarydetail.ui.postdetail.PostDetailViewModel
import com.example.primarydetail.ui.postlist.PostListScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import kotlinx.serialization.Serializable

@Serializable
object PostList : NavKey

@Serializable
data class PostDetail(val postId: Long) : NavKey

@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryEntryPoint {
        fun postDetailViewModelFactory(): PostDetailViewModel.Factory
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postDetailViewModelFactory = EntryPointAccessors.fromActivity(
            this,
            ViewModelFactoryEntryPoint::class.java
        ).postDetailViewModelFactory()

        setContent {

            val backStack = rememberNavBackStack(PostList)
            val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "App")
                        },
                        navigationIcon = {
                            // FIXME This is not right
                            if (backStack.size > 1) {
                                IconButton(onClick = { backStack.removeLastOrNull() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.back),
                                    )
                                }
                            }
                        }
                    )
                },
            ) { padding ->
                NavDisplay(
                    modifier = Modifier.padding(padding),
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
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
                            PostDetailScreen(
                                postId = post.postId,
                                viewModelFactory = postDetailViewModelFactory
                            )
                        }
                    }
                )
            }
        }
    }
}
