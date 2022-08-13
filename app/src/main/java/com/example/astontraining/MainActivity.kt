package com.example.astontraining

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

private const val KEY_COUNT = "count"
const val EXTRA_COUNT = "com.example.astontraining.extra.COUNT"

class MainActivity : AppCompatActivity() {

    // Amount of taps
    private var count = 0

    // References to Views
    private lateinit var showCountTextView: TextView
    private lateinit var helloButton: Button
    private lateinit var countButton: Button

    // Result receiver
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        showCountTextView = findViewById(R.id.show_count)
        helloButton = findViewById(R.id.button_hello)
        countButton = findViewById(R.id.button_count)

        // Set receiver
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                count = it.data!!.getIntExtra(EXTRA_REPLY, 0)
            }
        }

        // Set a ClickListener to the buttons
        helloButton.setOnClickListener { launchSecondActivity() }
        countButton.setOnClickListener { countUp() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Write the variable with the key in the Bundle
        outState.putInt(KEY_COUNT, count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restore the variable by the key
        count = savedInstanceState.getInt(KEY_COUNT)

        // Set the restored value to the TextView
        showCountTextView.text = count.toString()
    }

    /**
     * Launches the [SecondActivity].
     */
    private fun launchSecondActivity() {

        // Explicit Intent to launch the SecondActivity
        val launchIntent = Intent(this, SecondActivity::class.java)

        // Add the current amount of taps to the Intent as an extra
        launchIntent.putExtra(EXTRA_COUNT, count)

        // Start the SecondActivity
        getResult.launch(launchIntent)
    }

    /**
     * Increases the amount of taps by 1 and sets the current amount to the [TextView] show_count.
     */
    private fun countUp() {

        // Increase the amount of taps by 1
        count++

        // Set the current amount to the TextView
        showCountTextView.text = count.toString()
    }
}