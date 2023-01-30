package com.example.astontraining.viewmodel

import android.app.Activity
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.lifecycle.*
import com.example.astontraining.model.Contact
import com.example.astontraining.model.ContactTest
import com.example.astontraining.model.Repository
import com.example.astontraining.view.adapter.ContactsListAdapter
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] for providing data to fragments.
 */
class ContactsViewModel(private val repository: Repository) : ViewModel() {

    val contactsList = mutableListOf<ContactTest>()

    /**
     * The LiveData of the list of [Contact] objects from the database.
     */
    val contacts = repository.getAllContacts().asLiveData()

    /**
     * Retrieves the [Contact] from the database by id
     */
    fun retrieveContact(id: Int): LiveData<Contact> =
        repository.tempContactDao.getContact(id).asLiveData()

    /**
     * Adds a new [Contact] into the database.
     */
    fun addNewContact(name: String, surname: String, phoneNumber: String) {

        val newContact = Contact(
            name = name,
            surname = surname,
            phoneNumber = phoneNumber.toLong(),
            imageUrl = ""
        )

        viewModelScope.launch { repository.tempContactDao.insertContact(newContact) }
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

        val contact = Contact(id, name, surname, phoneNumber.toLong(), "")

        viewModelScope.launch { repository.tempContactDao.update(contact) }
    }

    /**
     * Deletes the [Contact] from the database.
     */
    fun deleteContact(contact: Contact) {
        viewModelScope.launch { repository.tempContactDao.delete(contact) }
    }

    /**
     * Returns true if the name and the surname are not empty and the phoneNumber is valid.
     */
    fun isValidEntry(name: String, surname: String, phoneNumber: String): Boolean {
        return name.isNotBlank() &&
                surname.isNotBlank() &&
                PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
    }

    /*fun getContacts(activity: Activity, adapter: ContactsListAdapter) {

        contactsList.clear()

        val cursor = activity.contentResolver
            .query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ), null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )

        while (cursor!!.moveToNext()) {
            val contactName = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            )
            val contactNumber = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            )

            val contact = ContactTest(
                name = contactName,
                phoneNumber = contactNumber
            )

            contactsList.add(contact)
        }

        adapter.submitList(contactsList)
        adapter.notifyDataSetChanged()

        cursor.close()
    }*/
}

/**
 * Factory class to instantiate the [ContactsViewModel].
 */
class ContactsViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(repository) as T
        }

        throw IllegalAccessException("Unknown ViewModel class")
    }
}