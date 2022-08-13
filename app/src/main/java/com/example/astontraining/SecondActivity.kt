package com.example.astontraining

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    // References to Views
    private lateinit var passageHeaderTextView: TextView
    private lateinit var passageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Find Views
        passageHeaderTextView = findViewById(R.id.passage_header)
        passageTextView = findViewById(R.id.passage)

        // Set a passage header and a passage from the Intent extra
        passageHeaderTextView.text = intent.getStringExtra(EXTRA_PASSAGE_HEADER)
        passageTextView.text = intent.getStringExtra(EXTRA_PASSAGE)
    }
}