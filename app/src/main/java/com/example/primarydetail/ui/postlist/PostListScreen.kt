package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primarydetail.R
import com.example.primarydetail.model.Post
import com.example.primarydetail.util.ToolbarActionItem


@ExperimentalFoundationApi
@Composable
fun PostListScreen(
    navigateToPostDetail: (Long) -> Unit,
    selectedPosts: (Int) -> Unit,
    actionModeActions: (List<ToolbarActionItem>) -> Unit,
    navigationAction: (ToolbarActionItem) -> Unit,
    toolbarTitle: (String) -> Unit,
    viewModel: PostListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    toolbarTitle(stringResource(id = R.string.title_post_list))
    actionModeActions(getActionModeActions(viewModel))
    navigationAction(getNavigationAction(viewModel))

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
                toggleSelected = toggleSelected,
                modifier = Modifier.animateItemPlacement()
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

@Composable
fun getActionModeActions(viewModel: PostListViewModel): List<ToolbarActionItem> {
    return listOf(
        ToolbarActionItem(
            Icons.Filled.Delete,
            stringResource(id = R.string.delete)
        ) { viewModel.deletePosts() },
        ToolbarActionItem(
            Icons.Filled.MarkEmailRead,
            stringResource(id = R.string.markRead)
        ) { viewModel.markRead() },
    )
}

@Composable
fun getNavigationAction(viewModel: PostListViewModel): ToolbarActionItem {
    return ToolbarActionItem(
        Icons.Filled.Close,
        stringResource(id = R.string.clear_selected)
    ) { viewModel.endSelection() }
}
