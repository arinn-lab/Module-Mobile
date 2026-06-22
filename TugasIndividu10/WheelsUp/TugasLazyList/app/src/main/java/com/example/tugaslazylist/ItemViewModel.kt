package com.example.tugaslazylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {

    val items = mutableStateListOf(
        Item(1, "Item Pertama", "Deskripsi item kesatu", R.drawable.img_item_1),
        Item(2, "Item Kedua", "Deskripsi item kedua", R.drawable.img_item_2, isToggled = true),
        Item(3, "Item Ketiga", "Deskripsi item ketiga", R.drawable.img_item_3),
        Item(4, "Item Keempat", "Deskripsi item keempat", R.drawable.img_item_4),
        Item(5, "Item Kelima", "Deskripsi item kelima", R.drawable.img_item_5, isToggled = true)
    )

    fun toggleItem(index: Int, isChecked: Boolean) {
        items[index] = items[index].copy(isToggled = isChecked)
    }
}