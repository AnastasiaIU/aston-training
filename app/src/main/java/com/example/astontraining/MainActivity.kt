package com.example.astontraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get Intent data
        val uri = intent.data

        if (uri != null) {

            // Create a string from the URI
            val uriString = getString(R.string.uri_label) + uri.toString()

            // Set the String to a TextView
            val textView = findViewById<TextView>(R.id.text_uri_message)
            textView.text = uriString
        }
    }
}