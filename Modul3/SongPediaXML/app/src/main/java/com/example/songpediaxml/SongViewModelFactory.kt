package com.example.songpediaxml

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SongViewModelFactory(private val application: Application, private val dataSource: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            return SongViewModel(application, dataSource) as T
        }
        throw IllegalArgumentException("ViewModel tidak dikenal")
    }
}