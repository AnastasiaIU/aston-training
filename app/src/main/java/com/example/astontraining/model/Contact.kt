package com.example.astontraining.model

import android.telephony.PhoneNumberUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.astontraining.model.ModelConstants.DATABASE_TABLE_NAME
import java.util.*

/**
 * The [Contact] entity represents a single row in the contacts database.
 * *
 * @property id the ID of the [Contact].
 * @property name the name of the [Contact].
 * @property surname the surname of the [Contact].
 * @property phoneNumber the phone number of the [Contact].
 * @property imageUrl the image URL of the [Contact] picture.
 */
@Entity(tableName = DATABASE_TABLE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var surname: String,
    var phoneNumber: Long,
    var imageUrl: String
) {

    // TODO: Remove from here.
    /**
     * Returns the formatted phone number.
     */
    fun getFormattedPhoneNumber(): String {
        return PhoneNumberUtils.formatNumber(phoneNumber.toString(), Locale.getDefault().country)
    }
}