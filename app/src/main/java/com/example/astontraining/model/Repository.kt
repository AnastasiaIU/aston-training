package com.example.astontraining.model

import com.example.astontraining.model.database.ContactDao
import kotlinx.coroutines.flow.Flow

/**
 * The [Repository] class provides access to the data layer of the application.
 */
class Repository(private val contactDao: ContactDao) {

    val tempContactDao = contactDao

    /**
     * Retrieves all [Contact] from the database ordered by name and surname in ascending order.
     * @return the list of [Contact] as flow data stream.
     */
    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getContacts()
    }
}