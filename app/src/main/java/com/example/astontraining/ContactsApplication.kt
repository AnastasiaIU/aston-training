package com.example.astontraining

import android.app.Application
import com.example.astontraining.model.Repository
import com.example.astontraining.model.database.ContactsDatabase

/**
 * [ContactsApplication] provides the single instances of
 * the database and of the repository for the application.
 */
class ContactsApplication : Application() {

    private val database by lazy { ContactsDatabase.getInstance(this) }

    /**
     * An instance of [Repository] class which
     * provides access to the data layer of the application.
     */
    val repository by lazy { Repository(database.contactDao()) }
}