package com.example.primarydetail.ui.postdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primarydetail.model.Post

@Composable
fun PostDetailScreen(post: Post) {
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
