package com.example.astontraining.model.database

import androidx.room.*
import com.example.astontraining.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object to access the contacts database.
 */
@Dao
interface ContactDao {

    /**
     * Retrieves all [Contact] from the database ordered by name and surname in ascending order.
     * @return the list of [Contact] as flow data stream.
     */
    @Query("SELECT * FROM contacts ORDER BY name AND surname")
    fun getContacts(): Flow<List<Contact>>

    // Retrieves the Contact from the database by id
    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contact>

    /**
     * Inserts the [contact] into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    // Updates the Contact in the database
    @Update
    suspend fun update(contact: Contact)

    // Deletes the Contact from the database
    @Delete
    suspend fun delete(contact: Contact)
}