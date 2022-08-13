package com.example.astontraining

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val LOG_TAG = "SecondActivity"
const val EXTRA_REPLY = "com.example.astontraining.extra.REPLY"

class SecondActivity : AppCompatActivity() {

    // References to Views
    private lateinit var secondButton: Button
    private lateinit var messageTextView: TextView
    private lateinit var replyEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

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

        Log.d(LOG_TAG, "End SecondActivity")

        // Finish the Activity
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }
}