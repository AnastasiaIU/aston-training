package com.example.astontraining.di.modules

import android.content.Context
import androidx.room.Room
import com.example.astontraining.model.ModelConstants.DATABASE_NAME
import com.example.astontraining.model.ModelConstants.PATH_TO_ASSET_DATABASE
import com.example.astontraining.model.database.ContactDao
import com.example.astontraining.model.database.ContactsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module with dependencies for Room database.
 */
@Module
class RoomDatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContactsDatabase(): ContactsDatabase {

        return Room
            .databaseBuilder(
                context,
                ContactsDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .createFromAsset(PATH_TO_ASSET_DATABASE)
            .build()
    }

    @Singleton
    @Provides
    fun provideContactDao(contactsDatabase: ContactsDatabase): ContactDao {
        return contactsDatabase.getContactDao()
    }
}