package com.example.astontraining.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.astontraining.R
import com.example.astontraining.model.Contact
import com.example.astontraining.databinding.ListItemContactBinding
import com.example.astontraining.model.ContactBase
import com.example.astontraining.model.ContactTest

/**
 * [ListAdapter] implementation for the [RecyclerView].
 */
class ContactsListAdapter(
    private val toContactDetail: (Int) -> Unit,
    private val deleteContact: (Contact) -> Boolean
) :
    ListAdapter<ContactBase, ContactsListAdapter.ContactViewHolder>(DiffCallback) {

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

        fun bind(contact: ContactBase) {

            /**
             * Defines an array that contains column names to move from the Cursor to the ListView.
             */
            /*@SuppressLint("InlinedApi")
            val FROM_COLUMNS: Array<String> =
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)*/

            /**
             * Defines an array that contains resource ids for the layout views
             * that get the Cursor column contents. The id is pre-defined in
             * the Android framework, so it is prefaced with "android.R.id".
             */
            /*val TO_IDS: IntArray = intArrayOf(binding.listItemContactNameAndSurname.id)

            val cursorAdapter = SimpleCursorAdapter(
                binding.root.context,
                binding.root.id,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0
            )*/

//            id = contact.id
//            currentContact = contact

//            val fullName = contact.name + " " + contact.surname
//            val phoneNumber = contact.getFormattedPhoneNumber()
            //val resId = getDrawableResId(contact.pictureId)

            binding.apply {
                listItemContactNameAndSurname.text = contact.name
//                listItemContactPhoneNumber.text = contact.phoneNumber
                //listItemContactImage.setImageResource(resId)
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

    companion object DiffCallback : DiffUtil.ItemCallback<ContactBase>() {

        override fun areItemsTheSame(oldItem: ContactBase, newItem: ContactBase): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ContactBase, newItem: ContactBase): Boolean {
            return oldItem == newItem// &&
//                    oldItem.surname == newItem.surname// &&
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