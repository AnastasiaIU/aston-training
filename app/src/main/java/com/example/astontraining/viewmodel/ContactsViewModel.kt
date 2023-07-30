package com.example.astontraining.viewmodel

import androidx.lifecycle.*
import com.example.astontraining.model.Contact
import com.example.astontraining.model.database.ContactsDatabase
import com.example.astontraining.model.Repository
import com.example.astontraining.viewmodel.ViewModelConstants.SEARCH_DELIMITER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class represents shared [ViewModel] and provides data for fragments.
 */
class ContactsViewModel(private val repository: Repository) : ViewModel() {

    /**
     * Current [Contact] to display.
     */
    val currentContact: MutableLiveData<Contact> = MutableLiveData()

    /**
     * All [Contact]s from [ContactsDatabase].
     */
    val contacts = repository.getAllContacts().asLiveData()

    /**
     * Current query from a search field.
     */
    val searchQuery: MutableLiveData<String> by lazy { MutableLiveData() }

    /**
     * Results of [Contact]s search at [ContactsDatabase].
     */
    val searchResults: MutableLiveData<List<Contact>> by lazy { MutableLiveData() }

    /**
     * Adds provided [contact] into [ContactsDatabase].
     */
    fun addNewContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) { repository.insert(contact) }
    }

    /**
     * Updates provided [contact] in [ContactsDatabase].
     */
    fun updateContact(contact: Contact) {

        viewModelScope.launch(Dispatchers.IO) { repository.update(contact) }

        currentContact.postValue(contact)
    }

    /**
     * Deletes provided [contact] from [ContactsDatabase].
     */
    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) { repository.delete(contact) }
    }

    /**
     * Performs a search for [Contact]s with [searchValue] at [ContactsDatabase]
     * and posts results to [ContactsViewModel].
     */
    suspend fun search(searchValue: String) {

        // List of values to search.
        val searchValueList = mutableListOf<String>()

        // List of search results.
        val searchResultList = mutableListOf<Contact>()

        searchValueList.apply {

            // Add all values for searching.
            addAll(searchValue.split(SEARCH_DELIMITER))

            forEachIndexed { index, string ->

                if (string.isNotBlank()) {

                    // Add all search results for the first value to the results.
                    if (index == 0) {
                        searchResultList.addAll(
                            repository.databaseSearch(string)
                        )
                    } else {

                        // Search results for the current string.
                        val currentSearchResult = repository.databaseSearch(string)

                        // Results of already performed searches.
                        val previousSearchResult = mutableListOf<Contact>()

                        previousSearchResult.apply {

                            // Add existing values.
                            addAll(searchResultList)

                            // Remove everything that does not match existing results.
                            forEach { contact ->
                                if (!currentSearchResult.contains(contact)) {
                                    searchResultList.remove(contact)
                                }
                            }
                        }
                    }
                }
            }
        }

        searchResults.postValue(searchResultList)
    }

    /**
     * Factory class for instantiating [ContactsViewModel].
     */
    class Factory @Inject constructor(private val repository: Repository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactsViewModel(repository) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}