package com.example.movieswithkajal.presentation.ui.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.stringResource
import com.example.movieswithkajal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    config: TopBarConfig,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(config.title) },
        navigationIcon = {
            if (config.showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
            }
        },
        actions = config.actions
    )
}
