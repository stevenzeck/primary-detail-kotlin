package com.example.primarydetail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.primarydetail.model.Post
import com.example.primarydetail.ui.postdetail.PostDetailScreen
import com.example.primarydetail.ui.postlist.PostListAdaptiveScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun PrimaryDetailApp() {

    val navigator = rememberListDetailPaneScaffoldNavigator<Post>()
    val coroutineScope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        coroutineScope.launch {
            navigator.navigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            }, navigationIcon = {
                if (navigator.canNavigateBack()) {
                    IconButton(onClick = { coroutineScope.launch { navigator.navigateBack() } }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                }
            })
        }, modifier = Modifier.fillMaxSize()
    ) { padding ->
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    PostListAdaptiveScreen(onPostSelected = { post ->
                        coroutineScope.launch {
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                post
                            )
                        }
                    })
                }
            },
            detailPane = {
                AnimatedPane {
                    navigator.currentDestination?.contentKey?.let {
                        PostDetailScreen(post = it)
                    }
                }
            },
            modifier = Modifier.padding(padding)
        )
    }
}
