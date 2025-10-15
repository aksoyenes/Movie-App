package com.example.movieaap.Navigation



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieaap.Screen.HomeScreen
import com.example.movieaap.Screen.MovieDetailScreen

import com.example.movieaap.Screen.SearchScreen
import com.example.movieaap.Screen.SettingsScreen
import com.example.movieaap.screen.MovieListScreen
import com.example.movieaap.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onSearchClick = { navController.navigate("search") },
                onProfileClick = { navController.navigate("profile") },
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onShowAllClick = { category ->
                    navController.navigate("movieList/$category")
                }
            )
        }
        composable("search") {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                },
                onNavigateUp = {
                    navController.popBackStack()
                }
            )
        }
        composable("settings") {
            SettingsScreen(navController = navController, themeViewModel = themeViewModel)
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(onBack = { navController.popBackStack() })
        }


        composable(
            route = "movieList/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->

            val category = backStackEntry.arguments?.getString("category") ?: ""
            MovieListScreen(
                category = category,
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                },
                onNavigateUp = { navController.popBackStack() }
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = {Text("Profile")}, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "")}})}) {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            Text("Profil Sayfası")
        }
    }
}