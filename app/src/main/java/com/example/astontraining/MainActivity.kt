package com.example.astontraining

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // References to Views
        val imageView = findViewById<ImageView>(R.id.image_view)
        val editText = findViewById<EditText>(R.id.edit_text)

        // Clear focus when Done button is clicked from soft-keyboard
        editText.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.clearFocus()
            }

            return@setOnEditorActionListener false
        }

        // Load an image when the EditText focus is lost and the EditText is not empty
        editText.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus && editText.text.isNotEmpty()) {

                // URL string
                val url = editText.text.toString()

                // Display the URL if it's valid else show a Toast message
                if (URLUtil.isValidUrl(url)) {

                    // URL object
                    val imageUrl = URL(url)

                    // Async task to download a Bitmap from the URL
                    val deferredBitmap = GlobalScope.async { imageUrl.toBitmap() }

                    GlobalScope.launch(Dispatchers.Main) {

                        // Get the downloaded Bitmap or null
                        val bitmap = deferredBitmap.await()

                        // If Bitmap is not null set it to the ImageView else show a Toast message
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap)
                        } else {
                            showToast(getString(R.string.toast_message_load))
                        }
                    }

                } else {
                    showToast(getString(R.string.toast_message_url))
                }
            }
        }
    }

    /**
     * Downloads a [Bitmap] from the [URL].
     *
     * @return [Bitmap] or null if data is not available.
     */
    private fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }
    }

    /**
     * Shows a [Toast] message.
     *
     * @param message The [String] message to display using the [Toast] notification.
     */
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }
}