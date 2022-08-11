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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the [TextView] show_count
        showCountTextView = findViewById(R.id.show_count)

        // Find [Button]s
        val toastButton: Button = findViewById(R.id.button_toast)
        val countButton: Button = findViewById(R.id.button_count)

        // Set click listeners on buttons
        toastButton.setOnClickListener { showToast() }
        countButton.setOnClickListener { countUp() }
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
    }

    /**
     * Shows a [Toast] message.
     */
    private fun showToast() {
        val toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT)
        toast.show()
    }

    /**
     * Increases the amount of taps by 1 and sets the current amount to the [TextView] show_count.
     */
    private fun countUp() {

        // Increase the amount of taps by 1
        count++

        // Set the current amount to the [TextView] show_count
        showCountTextView.text = count.toString()
    }
}