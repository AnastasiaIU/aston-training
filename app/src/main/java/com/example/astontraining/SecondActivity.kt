package com.example.astontraining

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_REPLY = "com.example.astontraining.extra.REPLY"

class SecondActivity : AppCompatActivity() {

    // References to Views
    private lateinit var secondButton: Button
    private lateinit var messageTextView: TextView
    private lateinit var replyEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Find Views
        secondButton = findViewById(R.id.button_second)
        messageTextView = findViewById(R.id.text_message)
        replyEditText = findViewById(R.id.editText_second)

        // Set a text from the Intent extra
        messageTextView.text = intent.getStringExtra(EXTRA_MESSAGE)

        // Set a ClickListener to the button
        secondButton.setOnClickListener { returnReply() }
    }

    /**
     * Sets [Intent] for a reply and finishes the [SecondActivity].
     */
    private fun returnReply() {

        // Intent for the response
        val replyIntent = Intent()

        // Text from the EditText
        val reply = replyEditText.text.toString()

        // Add the text to the Intent as an extra
        replyIntent.putExtra(EXTRA_REPLY, reply)

        // Set the OK result
        setResult(RESULT_OK, replyIntent)

        // Finish the Activity
        finish()
    }
}