package com.example.astontraining

import android.app.Application
import com.example.astontraining.data.ContactsDatabase

/**
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [ContactsDatabase].
 */
class BaseApplication : Application() {

    // Using by lazy so the database is only created when it is needed
    // rather than when the application starts
    val database: ContactsDatabase by lazy { ContactsDatabase.getDatabase(this) }
}