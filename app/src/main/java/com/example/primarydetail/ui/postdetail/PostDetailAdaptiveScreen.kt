package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.primarydetail.model.Post

@Composable
fun PostDetailScreen(post: Post, viewModel: PostDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.postDetailUiState.collectAsStateWithLifecycle()
    val currentUiState = uiState.value

    when (currentUiState) {
        is PostDetailUiState.Success -> {
            Column(modifier = Modifier.padding(20.dp)) {
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

        is PostDetailUiState.Failed -> {
            Text("Error: ${currentUiState.error.message}")
        }

        is PostDetailUiState.Loading -> {
            CircularProgressIndicator()
        }
    }
}
