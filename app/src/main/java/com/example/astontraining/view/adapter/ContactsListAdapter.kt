package com.example.astontraining.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.astontraining.R
import com.example.astontraining.databinding.ListItemContactBinding
import com.example.astontraining.model.Contact
import com.example.astontraining.view.Utility

/**
 * Adapter for displaying [Contact] in [RecyclerView].
 */
class ContactsListAdapter(private val adapterInterface: AdapterInterface) :
    ListAdapter<Contact, ContactsListAdapter.ContactViewHolder>(DiffCallback) {

    /**
     * Custom interface of [ContactsListAdapter].
     */
    interface AdapterInterface {
        fun openContactDetails(contact: Contact)

        fun deleteContact(contact: Contact): Boolean

        fun loadContactImage(contact: Contact, imageView: ImageView)
    }

    inner class ContactViewHolder(private val binding: ListItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds information of provided [contact] to [ContactViewHolder].
         */
        fun bindContact(contact: Contact) {

            binding.apply {

                listItemContactFullName.text = Utility.formatToFullName(
                    contact.name, contact.surname
                )

                listItemContactPhoneNumber.text = Utility.formatPhoneNumber(contact.phoneNumber)

                adapterInterface.loadContactImage(contact, listItemContactImage)

                root.setOnClickListener { adapterInterface.openContactDetails(contact) }

                root.setOnLongClickListener { setupPopupMenu(it, contact) }
            }
        }

        /**
         * Sets pop-up menu for deleting [contact].
         */
        private fun setupPopupMenu(view: View, contact: Contact): Boolean {

            val popupMenu = PopupMenu(view.context, view)

            popupMenu.apply {
                inflate(R.menu.popup_menu)
                setOnMenuItemClickListener { adapterInterface.deleteContact(contact) }
                show()
            }

            return true
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Contact>() {

        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ListItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindContact(getItem(position))
    }
}