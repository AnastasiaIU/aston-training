package com.example.astontraining.data

import android.telephony.PhoneNumberUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * The [Contact] entity represents a single row in the contacts database.
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val pictureId: Int = (0..14).random()
) {

    /**
     * Returns the formatted phone number.
     */
    fun getFormattedPhoneNumber(): String {
        return PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().country)
    }
}