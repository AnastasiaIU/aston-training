package com.example.astontraining

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

private const val KEY_REPLY_HEAD = "com.example.astontraining.REPLY_HEAD"
private const val KEY_REPLY = "com.example.astontraining.REPLY"
const val EXTRA_MESSAGE = "com.example.astontraining.extra.MESSAGE"

class MainActivity : AppCompatActivity() {

    // References to Views
    private lateinit var mainButton: Button
    private lateinit var messageEditText: EditText
    private lateinit var replyHeadTextView: TextView
    private lateinit var replyTextView: TextView

    // Result receiver for a started Intent
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        mainButton = findViewById(R.id.button_main)
        messageEditText = findViewById(R.id.editText_main)
        replyHeadTextView = findViewById(R.id.text_header_reply)
        replyTextView = findViewById(R.id.text_message_reply)

        // Set receiver
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                replyTextView.text = it.data?.getStringExtra(EXTRA_REPLY)
                replyHeadTextView.text = getString(R.string.text_header_reply)
                replyTextView.visibility = View.VISIBLE
                replyHeadTextView.visibility = View.VISIBLE
            }
        }

        // Set a ClickListener to the button
        mainButton.setOnClickListener { launchSecondActivity() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (replyTextView.text != null) {

            // Write variables with keys in the Bundle
            outState.putString(KEY_REPLY_HEAD, replyHeadTextView.text.toString())
            outState.putString(KEY_REPLY, replyTextView.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (!savedInstanceState.isEmpty) {

            // Restore variables by keys
            replyHeadTextView.text = savedInstanceState.getString(KEY_REPLY_HEAD)
            replyTextView.text = savedInstanceState.getString(KEY_REPLY)

            // Restore visibility
            replyHeadTextView.visibility = View.VISIBLE
            replyTextView.visibility = View.VISIBLE
        }
    }

    /**
     * Launches the [SecondActivity].
     */
    private fun launchSecondActivity() {

        // Explicit Intent to launch the SecondActivity
        val launchIntent = Intent(this, SecondActivity::class.java)

        // Text from the EditText
        val message = messageEditText.text.toString()

        // Add the text to the Intent as an extra
        launchIntent.putExtra(EXTRA_MESSAGE, message)

        // Start the SecondActivity
        getResult.launch(launchIntent)
    }
}