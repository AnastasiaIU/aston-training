package com.example.astontraining.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.*
import com.example.astontraining.data.Contact
import com.example.astontraining.data.ContactDao
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] for providing data to fragments and interaction for the [ContactDao].
 */
class ContactsViewModel(private val contactDao: ContactDao) : ViewModel() {

    // Cache all contacts form the database using LiveData
    val contacts: LiveData<List<Contact>> = contactDao.getContacts().asLiveData()

    /**
     * Retrieves the [Contact] from the database by id
     */
    fun retrieveContact(id: Int): LiveData<Contact> = contactDao.getContact(id).asLiveData()

    /**
     * Adds a new [Contact] into the database.
     */
    fun addNewContact(name: String, surname: String, phoneNumber: String) {

        val newContact = Contact(
            name = name,
            surname = surname,
            phoneNumber = phoneNumber
        )

        viewModelScope.launch { contactDao.insert(newContact) }
    }

    /**
     * Updates the [Contact] in the database.
     */
    fun updateContact(
        id: Int,
        name: String,
        surname: String,
        phoneNumber: String
    ) {

        val contact = Contact(id, name, surname, phoneNumber)

        viewModelScope.launch { contactDao.update(contact) }
    }

    /**
     * Deletes the [Contact] from the database.
     */
    fun deleteContact(contact: Contact) {
        viewModelScope.launch { contactDao.delete(contact) }
    }

    /**
     * Returns true if the name and the surname are not empty and the phoneNumber is valid.
     */
    fun isValidEntry(name: String, surname: String, phoneNumber: String): Boolean {
        return name.isNotBlank() &&
                surname.isNotBlank() &&
                PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
    }
}

/**
 * Factory class to instantiate the [ViewModel].
 */
class ContactsViewModelFactory(private val contactDao: ContactDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(contactDao) as T
        }

        throw IllegalAccessException("Unknown ViewModel class")
    }
}