package com.example.astontraining.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object to access the contacts database.
 */
@Dao
interface ContactDao {

    // Retrieves all Contacts from the database
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getContacts(): Flow<List<Contact>>

    // Retrieves the Contact from the database by id
    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contact>

    // Inserts a Contact into the database. Specify the conflict strategy as IGNORE,
    // when the user tries to add an existing Contact into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    // Updates the Contact in the database
    @Update
    suspend fun update(contact: Contact)

    // Deletes the Contact from the database
    @Delete
    suspend fun delete(contact: Contact)
}