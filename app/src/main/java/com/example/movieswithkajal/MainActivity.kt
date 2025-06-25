package com.example.movieswithkajal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.movieswithkajal.data.model.Movie
import com.example.movieswithkajal.presentation.navigation.TmdbNavGraph
import com.example.movieswithkajal.presentation.ui.TmdbTopBar
import com.example.movieswithkajal.presentation.viewmodel.HomeViewModel
import com.example.movieswithkajal.ui.theme.MoviesWithKajalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TmdbApp()
        }
    }
}

@Composable
fun TmdbApp() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val getMovieById: (Int) -> Movie? = { id ->
        homeViewModel.trending.value.data?.find { it.id == id }
            ?: homeViewModel.nowPlaying.value.data?.find { it.id == id }
    }
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = { TmdbTopBar(navController) }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    TmdbNavGraph(
                        navController = navController,
                        getMovieById = getMovieById
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesWithKajalTheme {
        TmdbApp()
    }
}