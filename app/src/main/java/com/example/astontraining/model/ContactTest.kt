package com.example.astontraining.model

import android.telephony.PhoneNumberUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.astontraining.model.ModelConstants.DATABASE_TABLE_NAME
import java.util.*

/**
 * The [ContactTest] entity represents a single row in the contacts database.
 * *
 * @property id the ID of the [ContactTest].
 * @property name the name of the [ContactTest].
 * @property surname the surname of the [ContactTest].
 * @property phoneNumber the phone number of the [ContactTest].
 * @property imageUrl the image URL of the [ContactTest] picture.
 */
data class ContactTest(
    var name: String,
    var phoneNumber: String
)