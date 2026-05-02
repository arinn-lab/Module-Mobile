package com.example.songpediaxml

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SongViewModel(application: Application) : AndroidViewModel(application) {

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val _selectedSong = MutableLiveData<Song>()
    val selectedSong: LiveData<Song> = _selectedSong

    init {
        _songs.value = SongData.getSongs(application)
    }

    fun selectSong(song: Song) {
        _selectedSong.value = song
    }
}