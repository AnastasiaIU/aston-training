package com.example.astontraining.contactdetail

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.astontraining.BaseApplication
import com.example.astontraining.data.Contact
import com.example.astontraining.databinding.FragmentContactDetailBinding
import com.example.astontraining.viewmodel.ContactsViewModel
import com.example.astontraining.viewmodel.ContactsViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * A fragment to enter data for a new [Contact] or edit data for an existing [Contact].
 */
class ContactDetailFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_CONTACT_ID = "contact_id"
    }

    private var contactId: Int? = null

    private var toolbarLayout: CollapsingToolbarLayout? = null

    private val navigationArgs: ContactDetailFragmentArgs by navArgs()

    private val viewModel: ContactsViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as BaseApplication).database.contactDao()
        )
    }

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    private val dragListener = View.OnDragListener { view, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            contactId = clipDataItem.text.toString().toInt()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //contactId = it.getInt(ARG_CONTACT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        toolbarLayout = binding.toolbarLayout
        binding.root.setOnDragListener(dragListener)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val id = navigationArgs.contactId // contactId ?: 0

        if (contactId != null) {
            viewModel.retrieveContact(contactId!!).observe(this.viewLifecycleOwner) {
                //contact = it
                bindContact(it)
            }
        }

        binding.contactDetailSaveButton.setOnClickListener { addContact() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindContact(contact: Contact) {
        binding.apply {

            contactDetailName.setText(contact.name, TextView.BufferType.SPANNABLE)
            contactDetailSurname.setText(contact.surname, TextView.BufferType.SPANNABLE)

            contactDetailPhoneNumber.setText(
                contact.getFormattedPhoneNumber(), TextView.BufferType.SPANNABLE
            )

            contactDetailSaveButton.setOnClickListener { updateContact() }
        }

    }

    private fun updateContact() {

        if (isValidEntry()) {

            viewModel.updateContact(
                id = 0,//navigationArgs.contactId,
                name = binding.contactDetailName.text.toString(),
                surname = binding.contactDetailSurname.text.toString(),
                phoneNumber = binding.contactDetailPhoneNumber.text.toString()
            )

            /*val action = ContactDetailFragmentDirections
                .actionContactDetailFragmentToContactDetailStartFragment()

            findNavController().navigate(action)*/
        } else {
            val toastMessage = "Wrong input"
            val toast = Toast.makeText(this.context, toastMessage, Toast.LENGTH_LONG)
            toast.show()
        }
    }

    private fun addContact() {

        if (isValidEntry()) {

            viewModel.addNewContact(
                binding.contactDetailName.text.toString(),
                binding.contactDetailSurname.text.toString(),
                binding.contactDetailPhoneNumber.text.toString()
            )

            /*val action = ContactDetailFragmentDirections
                .actionContactDetailFragmentToContactDetailStartFragment()

            findNavController().navigate(action)*/
        } else {
            val toastMessage = "Wrong input"
            val toast = Toast.makeText(this.context, toastMessage, Toast.LENGTH_LONG)
            toast.show()
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.contactDetailName.text.toString(),
        binding.contactDetailSurname.text.toString(),
        binding.contactDetailPhoneNumber.text.toString()
    )
}