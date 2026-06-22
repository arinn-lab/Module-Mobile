package com.example.tugaslazylist

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val imageRes: Int,
    var isToggled: Boolean = false
)