package com.example.primarydetail.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.SQLiteException
import com.example.primarydetail.R
import com.example.primarydetail.ui.PostRepository
import com.example.primarydetail.util.AppError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PostListUiState>(PostListUiState.Loading)
    val uiState: StateFlow<PostListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.getPosts().collect { posts ->
                    _uiState.value = PostListUiState.Success(
                        posts,
                    )
                }
            } catch (e: Exception) {
                _uiState.value = PostListUiState.Failed(mapExceptionToAppError(e))
            }
        }
    }

    fun markRead(postIds: List<Long>) = viewModelScope.launch {
        try {
            if (postIds.isNotEmpty()) {
                repository.markRead(postIds)
            }
        } catch (e: Exception) {
            _uiState.value = PostListUiState.Failed(mapExceptionToAppError(e))
        }
    }

    fun markRead(postId: Long) = viewModelScope.launch {
        repository.markRead(postId = postId)
    }

    fun deletePosts(postIds: List<Long>) = viewModelScope.launch {
        try {
            if (postIds.isNotEmpty()) {
                repository.deletePosts(postIds)
            }
        } catch (e: Exception) {
            _uiState.value = PostListUiState.Failed(mapExceptionToAppError(e))
        }
    }

    private fun mapExceptionToAppError(e: Throwable): AppError {
        return when (e) {
            is IOException -> AppError.NetworkError(
                R.string.error_network,
                specificMessage = e.message
            )

            is HttpException -> AppError.NetworkError(
                R.string.error_network_with_code,
                statusCode = e.code(),
                specificMessage = e.message()
            )

            is SQLiteException -> AppError.DatabaseError(
                R.string.error_database,
                specificMessage = e.message
            )

            else -> AppError.UnknownError(R.string.error_unknown, specificMessage = e.message)
        }
    }
}
