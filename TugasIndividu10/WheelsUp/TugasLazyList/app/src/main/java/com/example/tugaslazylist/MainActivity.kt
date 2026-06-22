package com.example.tugaslazylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tugaslazylist.ui.theme.TugasLazyListTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasLazyListTheme {
                ItemListScreen(viewModel = viewModel)
            }
        }
    }
}