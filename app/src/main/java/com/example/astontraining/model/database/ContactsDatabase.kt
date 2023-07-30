package com.example.astontraining.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astontraining.model.Contact

/**
 * Room database to persist data for the app.
 *
 * The database stores [Contact] entities.
 */
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactDao
}