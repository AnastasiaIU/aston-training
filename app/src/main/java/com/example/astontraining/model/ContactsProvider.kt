package com.example.astontraining.model

import android.app.Activity
import android.provider.ContactsContract
import com.example.astontraining.view.adapter.ContactsListAdapter

class ContactsProvider {

    fun getContacts(activity: Activity, adapter: ContactsListAdapter): List<ContactBase> {

        val contactsList = mutableListOf<ContactBase>()

        val projection = arrayOf(
            ContactsContract.Profile.DISPLAY_NAME,
            ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
            ContactsContract.Profile.DISPLAY_NAME_ALTERNATIVE,
            ContactsContract.Profile.DISPLAY_NAME_SOURCE,
            ContactsContract.Profile.PHONETIC_NAME,
            ContactsContract.Profile.PHONETIC_NAME_STYLE,
            ContactsContract.Profile.NAME_RAW_CONTACT_ID
        )

        val cursor = activity.contentResolver
            .query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Profile.DISPLAY_NAME
            )

        cursor?.let {

            while (it.moveToNext()) {

                val index = it.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME)

                val contactName: String? = if (index != -1) it.getString(index) else "-1"

                val contact = if (contactName != null) ContactBase(contactName) else ContactBase("null")

                contactsList.add(contact)
            }
        }

        adapter.submitList(contactsList)
        adapter.notifyDataSetChanged()

        cursor?.close()

        return contactsList
    }
}