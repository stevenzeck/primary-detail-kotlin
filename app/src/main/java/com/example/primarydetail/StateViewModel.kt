package com.example.primarydetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateViewModel : ViewModel() {

    private val _title: MutableStateFlow<String> by lazy { MutableStateFlow("Post List") }
    val title: StateFlow<String> by lazy { _title }

    fun updateTitle(title: String) {
        _title.value = title
    }
}
/*
TopAppBar for post detail
 */
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = stringResource(id = R.string.back),
//                        )
//                    }
//                },
//                actions = {
//                    IconButton(onClick = {
//                        postId?.let {
//                            viewModel.deletePost(postId)
//                            onBack()
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.Delete,
//                            contentDescription = stringResource(id = R.string.delete),
//                        )
//                    }
//                }


/*
TopAppBar for post list
 */
//            TopAppBar(
//                title = {
//                    Text(
//                        text = if (selectionMode) LocalContext.current.resources.getQuantityString(
//                            R.plurals.count_selected,
//                            selectedPosts.size,
//                            selectedPosts.size
//                        ) else stringResource(id = R.string.title_post_list)
//                    )
//                },
//                navigationIcon = if (selectionMode) {
//                    {
//                        IconButton(onClick = { viewModel.endSelection() }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = stringResource(id = R.string.clear_selected),
//                            )
//                        }
//                    }
//                } else {
//                    null
//                },
//                actions = {
//                    if (!selectionMode) {
//                        IconButton(onClick = navigateToSettings) {
//                            Icon(
//                                imageVector = Icons.Filled.Settings,
//                                contentDescription = stringResource(id = R.string.title_settings),
//                            )
//                        }
//                    } else {
//                        IconButton(onClick = { viewModel.deletePosts() }) {
//                            Icon(
//                                imageVector = Icons.Filled.Delete,
//                                contentDescription = stringResource(id = R.string.delete),
//                            )
//                        }
//                        IconButton(onClick = { viewModel.markRead() }) {
//                            Icon(
//                                imageVector = Icons.Filled.Drafts,
//                                contentDescription = stringResource(id = R.string.markRead),
//                            )
//                        }
//                    }
//                }
//            )


/*
TopAppBar for settings
 */
//topBar = {
//    TopAppBar(
//        title = { Text(text = stringResource(id = R.string.title_settings)) },
//        navigationIcon = {
//            IconButton(onClick = onBack) {
//                Icon(
//                    imageVector = Icons.Filled.ArrowBack,
//                    contentDescription = stringResource(id = R.string.back),
//                )
//            }
//        }
//    )
//},