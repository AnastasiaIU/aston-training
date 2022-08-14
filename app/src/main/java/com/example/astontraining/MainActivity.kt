package com.example.astontraining

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // References to Views
    private lateinit var openWebButton: Button
    private lateinit var openLocButton: Button
    private lateinit var shareTextButton: Button
    private lateinit var takePicButton: Button
    private lateinit var websiteEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var shareEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize references to Views
        openWebButton = findViewById(R.id.open_website_button)
        openLocButton = findViewById(R.id.open_location_button)
        shareTextButton = findViewById(R.id.share_text_button)
        takePicButton = findViewById(R.id.take_pic_button)
        websiteEditText = findViewById(R.id.website_edittext)
        locationEditText = findViewById(R.id.location_edittext)
        shareEditText = findViewById(R.id.share_edittext)

        // Set a ClickListener to the buttons
        openWebButton.setOnClickListener { openWebsite() }
        openLocButton.setOnClickListener { openLocation() }
        shareTextButton.setOnClickListener { shareText() }
        takePicButton.setOnClickListener { takePicture() }
    }

    /**
     * Opens an URL in a browser using implicit [Intent].
     */
    private fun openWebsite() {

        // Parse the passed string into a URI object
        val uri = Uri.parse(websiteEditText.text.toString())

        // Create a new Intent for viewing the URI object
        val viewIntent = Intent(Intent.ACTION_VIEW, uri)

        // Start an Activity
        startActivity(viewIntent)
    }

    /**
     * Opens a location in a map application using implicit [Intent].
     */
    private fun openLocation() {

        // Parse the passed string into a URI object with a geo search query
        val uri = Uri.parse("geo:0,0?q=" + locationEditText.text.toString())

        // Create a new Intent for viewing the URI object
        val viewIntent = Intent(Intent.ACTION_VIEW, uri)

        // Start an Activity
        startActivity(viewIntent)
    }

    /**
     * Shares a text using implicit [Intent].
     */
    private fun shareText() {

        // The text to share
        val text = shareEditText.text.toString()

        // The MIME type of the text to share
        val mimeType = "text/plain"

        // Create a new Intent for sharing the text
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = mimeType
        }
        val shareIntent = Intent.createChooser(sendIntent, null)

        // Start an Activity
        startActivity(shareIntent)
    }

    /**
     * Opens a camera application using implicit [Intent].
     */
    private fun takePicture() {

        // Create a new Intent to open a camera application
        val cameraIntent = Intent("android.media.action.IMAGE_CAPTURE")

        // Start an Activity
        startActivity(cameraIntent)
    }
}