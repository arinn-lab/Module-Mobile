package com.example.latihanrecyclerview

import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {

    val items: List<Item> = listOf(
        Item(1, "Item Pertama", "Deskripsi item kesatu", R.drawable.img_item_1),
        Item(2, "Item Kedua", "Deskripsi item kedua", R.drawable.img_item_2, isToggled = true),
        Item(3, "Item Ketiga", "Deskripsi item ketiga", R.drawable.img_item_3),
        Item(4, "Item Keempat", "Deskripsi item keempat", R.drawable.img_item_4),
        Item(5, "Item Kelima", "Deskripsi item kelima", R.drawable.img_item_5, isToggled = true)
    )
}