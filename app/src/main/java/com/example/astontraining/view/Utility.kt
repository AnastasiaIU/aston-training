package com.example.astontraining.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.telephony.PhoneNumberUtils
import android.widget.ImageView
import android.widget.Toast
import coil.imageLoader
import coil.request.ImageRequest
import java.util.*

/**
 * Utility object of the application.
 */
object Utility {

    /**
     * Returns formatted [phoneNumber].
     */
    fun formatPhoneNumber(phoneNumber: Long): String {
        return PhoneNumberUtils.formatNumber(phoneNumber.toString(), Locale.getDefault().country)
    }

    /**
     * Returns formatted [name] and [surname] as a full name.
     */
    fun formatToFullName(name: String, surname: String): String {
        return "$name $surname"
    }

    /**
     * Loads image from [data] source and sets it to the [imageView].
     *
     * Sets the [defaultDrawable] to the [imageView] if an error occurred during the loading.
     */
    fun loadImage(context: Context, data: Any?, imageView: ImageView, defaultDrawable: Drawable?) {

        val imageLoader = context.imageLoader

        val request = ImageRequest.Builder(context)
            .data(data)
            .target(
                onSuccess = { drawable -> imageView.setImageDrawable(drawable) },
                onError = { imageView.setImageDrawable(defaultDrawable) }
            )
            .build()

        imageLoader.enqueue(request)
    }

    /**
     * Returns true if provided [name] and [surname] are not empty
     * and provided [phoneNumber] is valid.
     */
    fun isValidEntry(name: String, surname: String, phoneNumber: String): Boolean {
        return name.isNotBlank() &&
                surname.isNotBlank() &&
                PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
    }

    /**
     * Shows [Toast] with provided [message].
     *
     * The [Toast] displayed for a short period of time.
     */
    fun showShortToast(context: Context, message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}