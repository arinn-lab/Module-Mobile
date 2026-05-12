package com.example.songpediaxml

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val year: Int,
    val genre: String,
    val descriptionEn: String,
    val descriptionId: String,
    val imageRes: Int,
    val spotifyUrl: String
)