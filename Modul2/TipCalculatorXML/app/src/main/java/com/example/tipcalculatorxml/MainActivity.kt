package com.example.tipcalculatorxml

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import java.text.NumberFormat
import java.util.*
import kotlin.math.ceil
class MainActivity : AppCompatActivity() {
    private lateinit var etBillAmount: EditText
    private lateinit var autoCompleteTip: AutoCompleteTextView
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var tvTipAmount: TextView

    private val tipPercentages = listOf(15.0, 18.0, 20.0)
    private var selectedTipPercentage = 15.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBillAmount = findViewById(R.id.etBillAmount)
        autoCompleteTip = findViewById(R.id.spinnerTipPercentage)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        tvTipAmount = findViewById(R.id.tvTipAmount)

        setupDropdown()
        setupListeners()
        calculateTip()
    }

    private fun setupDropdown() {
        val items = arrayOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        autoCompleteTip.setAdapter(adapter)
        autoCompleteTip.setOnItemClickListener { _, _, position, _ ->
            selectedTipPercentage = tipPercentages[position]
            calculateTip()
        }
    }

    private fun setupListeners() {
        etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { calculateTip() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchRoundUp.trackTintList = ColorStateList.valueOf(Color.parseColor("#E0E0E0"))
        switchRoundUp.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchRoundUp.trackTintList = ColorStateList.valueOf(Color.parseColor("#6750A4"))
            } else {
                switchRoundUp.trackTintList = ColorStateList.valueOf(Color.parseColor("#E0E0E0"))
            }
            calculateTip()
        }
    }

    private fun calculateTip() {
        val billAmountText = etBillAmount.text.toString()
        val billAmount = billAmountText.toDoubleOrNull() ?: 0.0
        var tip = billAmount * (selectedTipPercentage / 100.0)
        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        tvTipAmount.text = "Tip Amount: $formattedTip"
    }
}