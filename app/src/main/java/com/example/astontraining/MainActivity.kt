package com.example.astontraining

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Declaring and initializing the ImageView
        val imageView = findViewById<ImageView>(R.id.image_view)

        // Declaring executor to parse the URL
        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL and receives the image,
        // handler will load it in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap? = null

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Image URL
            val imageURL =
                "https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png"

            // Tries to get the image and post it in the ImageView with the help of Handler
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post { imageView.setImageBitmap(image) }
            }

            // If the URL does not point to image or any other kind of failure
            catch (e: Exception) {
                val toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}