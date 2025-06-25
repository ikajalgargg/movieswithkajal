package com.example.movieswithkajal.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieswithkajal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmdbTopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isHome = currentRoute == "home"

    TopAppBar(
        title = { Text(stringResource(R.string.TMDB_Movies)) },
        navigationIcon = {

            if (!isHome) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
            }
        },
        actions = {
            if (isHome) {
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search))
                }
                IconButton(onClick = { navController.navigate("bookmarks") }) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = stringResource(R.string.bookmarks))
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}