package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //binding == R.id.blabla
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button
        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    private fun calculateTip() {
        //Take COST from Edible Text
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        //val cost = stringInTextField.toDouble()
        //Test 1
        val cost = stringInTextField.toDoubleOrNull()
        if(cost == null || cost==0.0){
            displayTip(0.0) //clear tip amount
            return
        }

        //RadioButtons in RadioGroup to calculate percentage
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_20_percent -> 0.20
            R.id.option_18_percent -> 0.18
            else -> 0.15
        }

        //Calculate Tip
        var tip = tipPercentage * cost

        //Switch => Round or no tip??
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        // Display the formatted tip value on screen
        displayTip(tip)
    }
    private fun displayTip(tip: Double){
        //Number formatter to format numbers as currency.
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //RESULT
        binding.tipResult.text = getString(R.string.tip_ammount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}