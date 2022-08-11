package com.example.astontraining

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val KEY_COUNT = "count"

class MainActivity : AppCompatActivity() {

    // Amount of taps
    private var count = 0

    // Reference to the [TextView] show_count
    private lateinit var showCountTextView: TextView

    // Reference to the [Button] button_toast
    private lateinit var buttonToast: Button

    // Reference to the [Button] button_zero
    private lateinit var buttonZero: Button

    // Reference to the [Button] button_count
    private lateinit var buttonCount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the [TextView] show_count
        showCountTextView = findViewById(R.id.show_count)

        // Find [Button]s
        buttonToast = findViewById(R.id.button_toast)
        buttonZero = findViewById(R.id.button_zero)
        buttonCount = findViewById(R.id.button_count)

        // Set click listeners on buttons
        buttonToast.setOnClickListener { showToast() }
        buttonZero.setOnClickListener { resetCount() }
        buttonCount.setOnClickListener { countUp() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Write the variable with the key in the Bundle
        outState.putInt(KEY_COUNT, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore mCount variable by the key
        count = savedInstanceState.getInt(KEY_COUNT)

        // Set the restored value to the [TextView] show_count
        showCountTextView.text = count.toString()

        // Set colors for buttons
        setButtonsColors()
    }

    /**
     * Shows a [Toast] message.
     */
    private fun showToast() {
        val toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT)
        toast.show()
    }

    /**
     * Increases the amount of taps by 1, sets the current amount to the [TextView] show_count
     * and sets colors for buttons.
     */
    private fun countUp() {

        // Increase the amount of taps by 1
        count++

        // Set the current amount to the [TextView] show_count
        showCountTextView.text = count.toString()

        // Set colors for buttons
        setButtonsColors()
    }

    /**
     * Resets the amount of taps to 0, sets colors fot buttons
     * and updates the value at the [TextView] show_count.
     */
    private fun resetCount() {

        // Set the amount of taps to 0
        count = 0

        // Update the value at the TextView show_count
        showCountTextView.text = count.toString()

        // Set colors for buttons
        setButtonsColors()
    }

    /**
     * Sets colors for buttons.
     */
    private fun setButtonsColors() {

        // Get colors for the [Button]s
        val buttonZeroColor = if (count == 0) R.color.grey else R.color.pink
        val buttonCountColor = if (count % 2 == 0) R.color.blue else R.color.turquoise

        // Set colors for the [Button]s
        buttonZero.setBackgroundColor(getColor(buttonZeroColor))
        buttonCount.setBackgroundColor(getColor(buttonCountColor))
    }
}