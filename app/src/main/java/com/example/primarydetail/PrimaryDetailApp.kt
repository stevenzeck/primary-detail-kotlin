package com.example.primarydetail

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.postdetail.PostDetailScreen
import com.example.primarydetail.ui.postlist.PostListScreen
import com.example.primarydetail.ui.theme.PrimaryDetailTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp(fragmentManager: FragmentManager) {

    PrimaryDetailTheme {

        val appState = rememberPrimaryDetailState()
        var selectedPost: Post? by remember { mutableStateOf(null) }

        val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

        BackHandler(navigator.canNavigateBack()) {
            navigator.navigateBack()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = appState.topBarState.title)
                    },
                    navigationIcon = {
                        if (appState.showBackButton) {
                            // FIXME this is a jarring transition
                            IconButton(onClick = appState::upPress) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back),
                                )
                            }
                        } else appState.topBarState.navigationAction?.invoke()
                    },
                    actions = {
                        appState.topBarState.actions?.invoke(this)
                    }
                )
            },
        ) { paddingValues ->
            ListDetailPaneScaffold(
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {
                    AnimatedPane(Modifier.padding(paddingValues)) {
                        PostListScreen(
                            updateTopBarState = {
                                appState.topBarState = it
                            },
                            navigateToSettings = {},
                            onPostSelected = { post ->
                                selectedPost = post
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            },
                            resources = appState.resources
                        )
                    }
                },
                detailPane = {
                    AnimatedPane(Modifier.padding(paddingValues)) {
                        selectedPost?.let { post ->
                            PostDetailScreen(
                                updateTopBarState = {
                                    appState.topBarState = it
                                },
                                post = post,
                                onPostDeleted = { /*TODO*/ }
                            )
                        }
                    }
                },
            )
        }
    }
}
