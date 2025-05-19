package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primarydetail.util.AppError

@Suppress("UNCHECKED_CAST")
class LambdaViewModelFactory<T : ViewModel>(
    private val create: () -> T
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}

@Composable
fun PostDetailScreen(
    postId: Long,
    viewModelFactory: PostDetailViewModel.Factory
) {
    val viewModel: PostDetailViewModel = viewModel(
        key = "post_detail_$postId",
        factory = LambdaViewModelFactory { viewModelFactory.create(postId) }
    )

    val uiState = viewModel.postDetailUiState.collectAsStateWithLifecycle()
    val currentUiState = uiState.value

    when (currentUiState) {
        is PostDetailUiState.Success -> {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    SelectionContainer {
                        Text(text = currentUiState.post.title, fontSize = 30.sp)
                    }
                }
                Row {
                    SelectionContainer {
                        Text(text = currentUiState.post.body, fontSize = 18.sp)
                    }
                }
            }
        }

        is PostDetailUiState.Failed -> ErrorDisplayDetail(error = currentUiState.error)
        is PostDetailUiState.Loading -> LoadingDetail()
    }
}

@Composable
fun LoadingDetail() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun ErrorDisplayDetail(error: AppError, modifier: Modifier = Modifier) {
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
