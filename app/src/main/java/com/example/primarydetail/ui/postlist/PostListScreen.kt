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
    selectedPosts: (Int) -> Unit,
    viewModel: PostListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    when (val state: PostListUiState = uiState) {
        is PostListUiState.HasPosts -> {
            selectedPosts(state.selectedPosts.size)
            PostList(
                listState = listState,
                posts = state.posts,
                navigateToPostDetail = {
                    viewModel.markRead(it)
                    navigateToPostDetail(it)
                },
                selectionMode = state.selectionMode,
                selectedPosts = state.selectedPosts,
                startSelection = { id -> viewModel.startSelection(id) },
                toggleSelected = { id -> viewModel.toggleSelected(id) },
            )
        }
        is PostListUiState.NoPosts -> Loading()
    }
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
                post = post,
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