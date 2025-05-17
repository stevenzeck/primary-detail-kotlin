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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.primarydetail.model.Post

@ExperimentalFoundationApi
@Composable
fun PostListItem(
    post: Post,
    onPostSelected: (Long) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onPostSelected(post.id)
                },
            )
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                fontWeight = if (post.read) FontWeight.Normal else FontWeight.Bold,
            )
        }
    }
}
