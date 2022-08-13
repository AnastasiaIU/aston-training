package com.example.astontraining

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

const val EXTRA_REPLY = "com.example.astontraining.extra.REPLY"

class SecondActivity : AppCompatActivity() {

    // Reference to the View
    private lateinit var helloCountTextView: TextView

    // The count value from the starting Intent
    private var count by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Find the View
        helloCountTextView = findViewById(R.id.hello_count)

        // Set the count value
        count = intent.getIntExtra(EXTRA_COUNT, 0)

        // Set the count value to the TextView
        helloCountTextView.text = count.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Intent for a response
        val replyIntent = Intent()

        // Add the count value to the reply Intent as an extra
        replyIntent.putExtra(EXTRA_REPLY, count)

        // Set the OK result
        setResult(RESULT_OK, replyIntent)

        // Finish the Activity
        finish()
    }
}