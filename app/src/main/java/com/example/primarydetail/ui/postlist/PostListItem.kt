package com.example.primarydetail.ui.postlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.primarydetail.R
import com.example.primarydetail.model.Post

@ExperimentalFoundationApi
@Composable
fun PostListItem(
    post: Post,
    isRead: Boolean,
    onItemClicked: (Long) -> Unit,
    isSelectionMode: Boolean,
    isSelected: Boolean,
    startSelection: (Long) -> Unit,
    toggleSelected: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    if (isSelectionMode) {
                        toggleSelected(post.id)
                    } else {
                        onItemClicked(post.id)
                    }
                },
                onLongClickLabel = stringResource(id = R.string.multi_select_description),
                onLongClick = {
                    if (!isSelectionMode) {
                        startSelection(post.id)
                    }
                }
            )
            .fillMaxWidth()
            .background(color = if (isSelected) Color.LightGray else MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                fontWeight = if (isRead) FontWeight.Normal else FontWeight.Bold,
            )
        }
    }
}