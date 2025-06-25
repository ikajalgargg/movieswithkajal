package com.example.movieswithkajal.presentation.ui.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class TopBarConfig(
    val title: String,
    val showBackButton: Boolean = false,
    val actions: @Composable RowScope.() -> Unit = {}
)
