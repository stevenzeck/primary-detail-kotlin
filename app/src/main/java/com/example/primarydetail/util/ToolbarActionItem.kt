package com.example.primarydetail.util

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarActionItem(
    val icon: ImageVector? = null,
    val description: String? = null,
    val action: () -> Unit
)