package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.primarydetail.R
import com.example.primarydetail.model.Post
import org.koin.androidx.compose.getViewModel


@ExperimentalFoundationApi
@Composable
fun PostListScreen(navigateToPostDetail: (Long) -> Unit, navigateToSettings: () -> Unit) {
    val viewModel = getViewModel<PostListViewModel>()
    val listState = rememberLazyListState()
    val posts: PostListViewModel.PostListScreenState = viewModel.posts.collectAsState().value
    val selectionMode = viewModel.selectionMode.collectAsState().value
    val selectedPosts = viewModel.selectedPosts.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectionMode) LocalContext.current.resources.getQuantityString(
                            R.plurals.count_selected,
                            selectedPosts.size,
                            selectedPosts.size
                        ) else stringResource(id = R.string.title_post_list)
                    )
                },
                navigationIcon = if (selectionMode) {
                    {
                        IconButton(onClick = { viewModel.endSelection() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.clear_selected),
                            )
                        }
                    }
                } else {
                    null
                },
                actions = {
                    if (!selectionMode) {
                        IconButton(onClick = navigateToSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = stringResource(id = R.string.title_settings),
                            )
                        }
                    } else {
                        IconButton(onClick = { viewModel.deletePosts() }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(id = R.string.delete),
                            )
                        }
                        IconButton(onClick = { viewModel.markRead() }) {
                            Icon(
                                imageVector = Icons.Filled.Drafts,
                                contentDescription = stringResource(id = R.string.markRead),
                            )
                        }
                    }
                }
            )
        },
        content = {
            when (posts) {
                is PostListViewModel.PostListScreenState.Success -> PostList(
                    listState,
                    posts.data,
                    navigateToPostDetail,
                    selectionMode,
                    selectedPosts,
                    { id -> viewModel.startSelection(id) },
                    { id -> viewModel.toggleSelected(id) }
                )
                else -> Loading()
            }
        }
    )
}

@ExperimentalFoundationApi
@Composable
fun PostList(
    listState: LazyListState,
    posts: List<Post>,
    navigateToPostDetail: (Long) -> Unit,
    selectionMode: Boolean,
    selectedPosts: List<Long>,
    startSelection: (Long) -> Unit,
    toggleSelected: (Long) -> Unit
) {
    LazyColumn(state = listState) {
        items(
            items = posts,
            key = { post ->
                post.id
            }
        ) { post ->
            PostListItem(
                post,
                navigateToPostDetail,
                selectionMode,
                selectionMode && post.id in selectedPosts,
                startSelection,
                toggleSelected
            )
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun Loading() {
    Text(text = "Loading...")
}