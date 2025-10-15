package com.example.movieaap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieaap.Data.ThemeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val themeDataStore: ThemeDataStore) : ViewModel() {

    // DataStore'dan gelen tema durumunu bir StateFlow'a çeviriyoruz.
    // Bu sayede UI, tema durumunu anlık olarak takip edebilir.
    val isDarkMode: StateFlow<Boolean> = themeDataStore.getThemePreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false // Başlangıç değeri
    )

    // Tema durumunu değiştiren fonksiyon.
    fun toggleTheme() {
        viewModelScope.launch {
            // Mevcut değerin tersini DataStore'a kaydediyoruz.
            themeDataStore.saveThemePreference(!isDarkMode.value)
        }
    }
}