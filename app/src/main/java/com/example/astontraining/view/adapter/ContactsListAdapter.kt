package com.example.astontraining.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.astontraining.R
import com.example.astontraining.model.Contact
import com.example.astontraining.databinding.ListItemContactBinding

/**
 * [ListAdapter] implementation for the [RecyclerView].
 */
class ContactsListAdapter(
    private val toContactDetail: (Int) -> Unit,
    private val deleteContact: (Contact) -> Boolean
) :
    ListAdapter<Contact, ContactsListAdapter.ContactViewHolder>(DiffCallback) {

    class ContactViewHolder(
        private var binding: ListItemContactBinding,
        private val toContactDetail: (Int) -> Unit,
        private val deleteContact: (Contact) -> Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        private var id = 0
        private lateinit var currentContact: Contact

        init {

            binding.root.setOnClickListener {
                toContactDetail(id)
            }

            binding.root.setOnLongClickListener {

                val popupMenu = PopupMenu(it.context, it)

                popupMenu.apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { deleteContact(currentContact) }
                    show()
                }

                true
            }
        }

        fun bind(contact: Contact) {

            id = contact.id
            currentContact = contact

            val fullName = contact.name + " " + contact.surname
            val phoneNumber = contact.getFormattedPhoneNumber()
//            val resId = getDrawableResId(contact.pictureId)

            binding.apply {
                listItemContactNameAndSurname.text = fullName
                listItemContactPhoneNumber.text = phoneNumber
//                listItemContactImage.setImageResource(resId)
            }
        }

        private fun getDrawableResId(id: Int): Int {
            return when (id) {
                0 -> R.drawable.id0
                1 -> R.drawable.id1
                2 -> R.drawable.id2
                3 -> R.drawable.id3
                4 -> R.drawable.id4
                5 -> R.drawable.id5
                6 -> R.drawable.id6
                7 -> R.drawable.id7
                8 -> R.drawable.id8
                9 -> R.drawable.id9
                10 -> R.drawable.id10
                11 -> R.drawable.id11
                12 -> R.drawable.id12
                13 -> R.drawable.id13
                14 -> R.drawable.id14
                else -> R.drawable.no_profile_picture_icon
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Contact>() {

        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.surname == newItem.surname// &&
//                    oldItem.phoneNumber == newItem.phoneNumber// &&
//                    oldItem.pictureId == newItem.pictureId
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        return ContactViewHolder(
            ListItemContactBinding.inflate(LayoutInflater.from(parent.context)),
            toContactDetail,
            deleteContact
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        val current = getItem(position)

        holder.bind(current)
    }
}