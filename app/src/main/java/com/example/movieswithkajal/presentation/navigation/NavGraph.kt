package com.example.movieswithkajal.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.movieswithkajal.R
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.presentation.ui.BookmarksScreen
import com.example.movieswithkajal.presentation.ui.HomeScreen
import com.example.movieswithkajal.presentation.ui.MovieDetailScreen
import com.example.movieswithkajal.presentation.ui.SearchScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmarks : Screen("bookmarks")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}

@Composable
fun TmdbNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    getMovieById: (Int) -> Movie?
) {
    NavHost(navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen(onMovieClick = { movie ->
                navController.navigate(Screen.Detail.createRoute(movie.id))
            })
        }
        composable(Screen.Search.route) {
            SearchScreen(onMovieClick = { movieId ->
                navController.navigate(Screen.Detail.createRoute(movieId))
            })
        }
        composable(Screen.Bookmarks.route) {
            BookmarksScreen(onMovieClick = { movieId ->
                navController.navigate(Screen.Detail.createRoute(movieId))
            })
        }
        composable(
            route = Screen.Detail.route,
            deepLinks = listOf(navDeepLink { uriPattern = "myapp://movie/{movieId}" })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val movie = id?.let { getMovieById(it) }
            if (movie != null) {
                MovieDetailScreen(movieId = movie.id)
            } else {
                Text(stringResource(R.string.movie_not_found))
            }
        }
    }
}
