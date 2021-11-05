package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primarydetail.model.Post

@Composable
fun PostDetailScreen(
    postId: Long?,
    onBack: () -> Unit,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    postId?.let { viewModel.retrievePost(it) }
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.post != null) {
        PostDetailContent(
            post = uiState.post!!,
        )
    }

    // Check for failures while loading the state
    // TODO: Improve UX
    LaunchedEffect(uiState) {
        if (uiState.failedLoading) {
            onBack()
        }
    }
}

@Composable
fun PostDetailContent(post: Post) {
    Column {
        Row(modifier = Modifier.padding(20.dp)) {
            SelectionContainer {
                Text(text = post.title, fontSize = 30.sp)
            }
        }
        Row(modifier = Modifier.padding(20.dp)) {
            SelectionContainer {
                Text(text = post.body, fontSize = 18.sp)
            }
        }
    }
}
