package com.example.astontraining.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.astontraining.model.Contact
import com.example.astontraining.model.ModelConstants.DATABASE_NAME

/**
 * Room database to persist data for the app.
 *
 * This database stores [Contact] entities.
 */
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {

            // If the INSTANCE is not null, then return it,
            // if it is, then create a database from the asset.
            return INSTANCE ?: synchronized(this) {

                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/contacts_database.db")
                    .build()

                INSTANCE = instance

                return instance
            }
        }
    }
}