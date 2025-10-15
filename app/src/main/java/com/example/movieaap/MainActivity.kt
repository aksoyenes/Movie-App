package com.example.movieaap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieaap.Data.ThemeDataStore
import com.example.movieaap.Navigation.AppNavigation // Navigasyon dosyanızın yolunu kontrol edin
import com.example.movieaap.ui.theme.MovieAApTheme
import com.example.movieaap.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {


    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val themeDataStore = ThemeDataStore(applicationContext)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                return ThemeViewModel(themeDataStore) as T
            }
        }
        themeViewModel = ViewModelProvider(this, factory)[ThemeViewModel::class.java]


        setContent {

            val isDarkMode by themeViewModel.isDarkMode.collectAsState()


            MovieAApTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation(themeViewModel = themeViewModel)
                }
            }
        }
    }
}