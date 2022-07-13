package com.example.primarydetail.ui.postlist

import android.content.res.Resources
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.primarydetail.util.TopBarState


@ExperimentalFoundationApi
@Composable
fun PostListScreen(
    updateTopBarState: (TopBarState) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToPostDetail: (Long) -> Unit,
    viewModel: PostListViewModel = hiltViewModel(),
    resources: Resources
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    UpdateTopBarState(
        updateTopBarState = updateTopBarState,
        navigateToSettings = navigateToSettings,
        numSelectedPosts = 0,
        resources = resources,
        viewModel = viewModel,
    )

    when (val state: PostListUiState = uiState) {
        is PostListUiState.HasPosts -> {
            UpdateTopBarState(
                updateTopBarState = updateTopBarState,
                navigateToSettings = navigateToSettings,
                numSelectedPosts = state.selectedPosts.size,
                resources = resources,
                viewModel = viewModel
            )
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
fun UpdateTopBarState(
    updateTopBarState: (TopBarState) -> Unit,
    navigateToSettings: () -> Unit,
    numSelectedPosts: Int,
    resources: Resources,
    viewModel: PostListViewModel
) {
    updateTopBarState(
        TopBarState(
            title = if (numSelectedPosts > 0) {
                resources.getQuantityString(
                    R.plurals.count_selected,
                    numSelectedPosts,
                    numSelectedPosts
                )
            } else {
                stringResource(id = R.string.title_post_list)
            },
            navigationAction = {
                if (numSelectedPosts > 0) {
                    IconButton(onClick = { viewModel.endSelection() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.clear_selected)
                        )
                    }
                }
            },
            actions = {
                if (numSelectedPosts == 0) {
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.title_settings),
                        )
                    }
                } else if (numSelectedPosts > 0) {
                    IconButton(onClick = { viewModel.deletePosts() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                        )
                    }
                    IconButton(onClick = { viewModel.markRead() }) {
                        Icon(
                            imageVector = Icons.Filled.MarkEmailRead,
                            contentDescription = stringResource(id = R.string.markRead),
                        )
                    }
                }
            }
        )
    )
}
