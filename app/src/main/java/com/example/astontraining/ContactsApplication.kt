package com.example.astontraining

import android.app.Application
import android.content.Context
import com.example.astontraining.di.AppComponent
import com.example.astontraining.di.DaggerAppComponent
import com.example.astontraining.di.modules.RoomDatabaseModule

/**
 * This extension returns [AppComponent].
 */
val Context.appComponent: AppComponent
    get() = when (this) {
        is ContactsApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

/**
 * This class provides a single instance of [AppComponent].
 */
class ContactsApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .roomDatabaseModule(RoomDatabaseModule(applicationContext))
            .build()
    }
}