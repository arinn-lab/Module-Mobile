package com.example.compose

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Bundle
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerApp()
        }
    }
}

@SuppressLint("SetTextI18n")
@Composable
fun DiceRollerApp() {
    var dice1 by remember { mutableIntStateOf(0) }
    var dice2 by remember { mutableIntStateOf(0) }
    var resultMessage by remember { mutableStateOf("") }

    fun getDrawableForDice(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a2e)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(
                    id = if (dice1 == 0) R.drawable.dice_0 else getDrawableForDice(dice1)
                ),
                contentDescription = "Dice 1",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
            Image(
                painter = painterResource(
                    id = if (dice2 == 0) R.drawable.dice_0 else getDrawableForDice(dice2)
                ),
                contentDescription = "Dice 2",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dice1 = (1..6).random()
                dice2 = (1..6).random()
                resultMessage = if (dice1 == dice2) {
                    "Selamat, anda dapat dadu double!"
                } else {
                    "Anda belum beruntung!"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc9b8e8)),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Roll", color = Color(0xFF1a1a2e))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = resultMessage,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
