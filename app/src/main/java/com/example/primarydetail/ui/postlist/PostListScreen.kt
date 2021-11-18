package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primarydetail.model.Post


@ExperimentalFoundationApi
@Composable
fun PostListScreen(
    navigateToPostDetail: (Long) -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: PostListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()
    val selectionMode = viewModel.selectionMode.collectAsState().value
    val selectedPosts = viewModel.selectedPosts.collectAsState().value
    when (uiState) {
        is PostListUiState.HasPosts -> PostList(
            listState = listState,
            posts = (uiState as PostListUiState.HasPosts).posts,
            read = (uiState as PostListUiState.HasPosts).read,
            navigateToPostDetail = {
                viewModel.markRead(it)
                navigateToPostDetail(it)
            },
            selectionMode = selectionMode,
            selectedPosts = selectedPosts,
            startSelection = { id -> viewModel.startSelection(id) },
            toggleSelected = { id -> viewModel.toggleSelected(id) }
        )
        else -> Loading()
    }
}

@ExperimentalFoundationApi
@Composable
fun PostList(
    listState: LazyListState,
    posts: List<Post>,
    read: List<Long>,
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
                post = post,
                isRead = read.contains(post.id),
                onItemClicked = navigateToPostDetail,
                isSelectionMode = selectionMode,
                isSelected = selectionMode && post.id in selectedPosts,
                startSelection = startSelection,
                toggleSelected = toggleSelected
            )
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun Loading() {
    Text(text = "Loading...")
}