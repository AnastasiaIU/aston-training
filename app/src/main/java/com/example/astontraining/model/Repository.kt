package com.example.astontraining.model

import com.example.astontraining.model.database.ContactDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This class provides access to the data layer of the application.
 */
class Repository @Inject constructor(private val contactDao: ContactDao) {

    /**
     * Calls [ContactDao.getAllContacts] and returns its result.
     */
    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }

    /**
     * Calls [ContactDao.insert] with provided [contact].
     */
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    /**
     * Calls [ContactDao.update] with provided [contact].
     */
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    /**
     * Calls [ContactDao.delete] with provided [contact].
     */
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

    /**
     * Calls [ContactDao.searchAtDatabase] with provided [query].
     */
    suspend fun databaseSearch(query: String): List<Contact> {
        return contactDao.searchAtDatabase(query)
    }
}