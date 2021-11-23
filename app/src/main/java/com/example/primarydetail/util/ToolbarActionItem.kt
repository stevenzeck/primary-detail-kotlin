package com.example.primarydetail.util

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarActionItem(val icon: ImageVector, val description: String, val action: () -> Unit)