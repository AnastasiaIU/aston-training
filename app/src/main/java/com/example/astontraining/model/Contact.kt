package com.example.astontraining.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.astontraining.model.ModelConstants.DATABASE_TABLE_NAME
import com.example.astontraining.model.database.ContactsDatabase

/**
 * This class represents a single row in the [ContactsDatabase].
 *
 * @property id [Contact]'s ID.
 * @property name [Contact]'s name.
 * @property surname [Contact]'s surname.
 * @property phoneNumber [Contact]'s phone number.
 * @property imageUrl Image URL of the [Contact]'s picture.
 */
@Entity(tableName = DATABASE_TABLE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var surname: String,
    var phoneNumber: Long,
    var imageUrl: String
)