package com.example.xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivDice1 = findViewById<ImageView>(R.id.ivDice1)
        val ivDice2 = findViewById<ImageView>(R.id.ivDice2)
        val btnRoll = findViewById<Button>(R.id.btnRoll)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnRoll.setOnClickListener {

            val dice1 = (1..6).random()
            val dice2 = (1..6).random()

            ivDice1.setImageResource(getDrawableForDice(dice1))
            ivDice2.setImageResource(getDrawableForDice(dice2))

            if (dice1 == dice2) {
                tvResult.text = "Selamat, anda dapat dadu double!"
            } else {
                tvResult.text = "Anda belum beruntung!"
            }
        }
    }
    private fun getDrawableForDice(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}