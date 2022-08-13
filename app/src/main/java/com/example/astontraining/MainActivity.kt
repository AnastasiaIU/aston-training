package com.example.astontraining

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_PASSAGE_HEADER = "com.example.astontraining.extra.PASSAGE_HEADER"
const val EXTRA_PASSAGE = "com.example.astontraining.extra.PASSAGE"

class MainActivity : AppCompatActivity() {

    // References to Views
    private lateinit var firstButton: Button
    private lateinit var secondButton: Button
    private lateinit var thirdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        firstButton = findViewById(R.id.first_button)
        secondButton = findViewById(R.id.second_button)
        thirdButton = findViewById(R.id.third_button)

        // Set a ClickListener to the buttons
        firstButton.setOnClickListener {
            openPassage(
                getString(R.string.first_passage_header),
                getString(R.string.first_passage)
            )
        }

        secondButton.setOnClickListener {
            openPassage(
                getString(R.string.second_passage_header),
                getString(R.string.second_passage)
            )
        }

        thirdButton.setOnClickListener {
            openPassage(
                getString(R.string.third_passage_header),
                getString(R.string.third_passage)
            )
        }
    }

    /**
     * Launches the SecondActivity.
     */
    private fun openPassage(passageHeader: String, passage: String) {

        // Explicit Intent to launch the SecondActivity
        val launchIntent = Intent(this, SecondActivity::class.java)

        // Add a passage header and a passage to the Intent as an extra
        launchIntent.putExtra(EXTRA_PASSAGE_HEADER, passageHeader)
        launchIntent.putExtra(EXTRA_PASSAGE, passage)

        // Start the SecondActivity
        startActivity(launchIntent)
    }
}