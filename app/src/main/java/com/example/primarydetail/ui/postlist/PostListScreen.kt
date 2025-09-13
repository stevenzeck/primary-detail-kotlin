package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.primarydetail.R
import com.example.primarydetail.model.Post

@ExperimentalFoundationApi
@Composable
fun PostListScreen(
    onPostSelected: (Long) -> Unit,
    viewModel: PostListViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.postListUiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is PostListUiState.Success -> {
            if (currentState.posts.isEmpty()) {
                EmptyContentView(message = stringResource(R.string.no_posts_available))
            } else {
                PostList(
                    listState = listState,
                    posts = currentState.posts,
                    onPostSelected = { postId ->
                        viewModel.markRead(postId)
                        onPostSelected(postId)
                    },
                )
            }
        }

        is PostListUiState.Failed -> {
            ErrorStateView(
                errorMessage = currentState.error.localizedMessage
                    ?: stringResource(R.string.error_prefix_text),
                onRetry = { viewModel.loadPosts(forceServerRefresh = true) }
            )
        }

        is PostListUiState.Loading -> {
            LoadingView()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PostList(
    listState: LazyListState,
    posts: List<Post>,
    onPostSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(
            items = posts,
            key = { post ->
                post.id
            }
        ) { post ->
            PostListItem(
                post = post,
                onPostSelected = onPostSelected,
                modifier = Modifier.animateItem()
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorStateView(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_prefix_text, errorMessage),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry_button_text))
        }
    }
}

@Composable
fun EmptyContentView(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.headlineSmall)
    }
}
