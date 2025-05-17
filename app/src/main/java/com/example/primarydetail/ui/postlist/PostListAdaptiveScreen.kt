package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.primarydetail.model.Post
import com.example.primarydetail.util.AppError

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostListAdaptiveScreen(
    onPostSelected: (Post) -> Unit,
    viewModel: PostListViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is PostListUiState.Success -> {
            PostListAdaptive(
                listState = listState,
                posts = currentState.posts,
                onPostSelected = { post ->
                    viewModel.markRead(post.id)
                    onPostSelected(post)
                })
        }

        is PostListUiState.Failed -> {
            ErrorDisplay(error = currentState.error)
        }

        is PostListUiState.Loading -> Loading()
    }
}

@ExperimentalFoundationApi
@Composable
fun PostListAdaptive(
    listState: LazyListState, posts: List<Post>, onPostSelected: (Post) -> Unit
) {
    LazyColumn(state = listState) {
        items(items = posts, key = { post ->
            post.id
        }) { post ->
            PostListAdaptiveItem(
                post = post, onPostSelected = onPostSelected, modifier = Modifier.animateItem()
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PostListAdaptiveItem(
    post: Post,
    onPostSelected: (Post) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPostSelected(post) }
            .padding(16.dp)) {
        Text(
            text = post.title,
            fontWeight = if (post.read) FontWeight.Normal else FontWeight.Bold,
        )
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ErrorDisplay(error: AppError, modifier: Modifier = Modifier) {
    val errorMessage = when (error) {
        is AppError.NetworkError -> error.statusCode?.let {
            stringResource(id = error.messageResource, it)
        } ?: stringResource(id = error.messageResource)

        is AppError.DatabaseError -> stringResource(id = error.messageResource)
        is AppError.UnknownError -> stringResource(id = error.messageResource)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    }
}
