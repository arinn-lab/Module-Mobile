package com.example.songpediacompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.songpediacompose.Song
import com.example.songpediacompose.data.SongData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class SongViewModel(application: Application, private val dataSource: String) : AndroidViewModel(application) {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _selectedSong = MutableStateFlow<Song?>(null)
    val selectedSong: StateFlow<Song?> = _selectedSong

    private val _navigateToDetail = MutableStateFlow(false)
    val navigateToDetail: StateFlow<Boolean> = _navigateToDetail

    private val _openSpotify = MutableStateFlow<String?>(null)
    val openSpotify: StateFlow<String?> = _openSpotify

    init {
        _songs.value = SongData.getSongs(application)
        Timber.d("Data lagu masuk ke list dari sumber: $dataSource, total: ${_songs.value.size} lagu")
    }

    fun selectSong(song: Song) {
        _selectedSong.value = song
        Timber.d("Lagu dipilih: ${song.title} oleh ${song.artist}")
        _navigateToDetail.value = true
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = false
    }

    fun onSpotifyClicked(song: Song) {
        Timber.d("Tombol Spotify ditekan untuk lagu: ${song.title}")
        _openSpotify.value = song.spotifyUrl
    }

    fun onSpotifyOpened() {
        _openSpotify.value = null
    }
}