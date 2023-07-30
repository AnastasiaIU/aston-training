package com.example.astontraining.model.database

import androidx.room.*
import com.example.astontraining.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object to access [ContactsDatabase].
 */
@Dao
interface ContactDao {

    /**
     * Retrieves all [Contact]s from [ContactsDatabase] ordered by name and surname
     * in ascending order.
     *
     * @return [Flow] with [List] of [Contact]s.
     */
    @Query("SELECT * FROM contacts ORDER BY name, surname")
    fun getAllContacts(): Flow<List<Contact>>

    /**
     * Inserts provided [contact] into [ContactsDatabase].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    /**
     * Updates provided [contact] in [ContactsDatabase].
     */
    @Update
    suspend fun update(contact: Contact)

    /**
     * Deletes provided [contact] from [ContactsDatabase].
     */
    @Delete
    suspend fun delete(contact: Contact)

    /**
     * Performs a search at [ContactsDatabase] with provided [searchQuery]
     * at name and surname columns.
     *
     * @return result of the search as [List] of [Contact]s
     * ordered by name and surname in ascending order.
     */
    @Query(
        "SELECT * FROM contacts WHERE name LIKE '%' || :searchQuery || '%' " +
                "OR surname LIKE '%' || :searchQuery || '%' ORDER BY name, surname"
    )
    suspend fun searchAtDatabase(searchQuery: String): List<Contact>
}