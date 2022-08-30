package com.example.astontraining.contactdetail

import android.os.Bundle
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

/**
 * A fragment to enter data for a new [Contact] or edit data for an existing [Contact].
 */
class ContactDetailFragment : Fragment() {

    private val navigationArgs: ContactDetailFragmentArgs by navArgs()

    private val viewModel: ContactsViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as BaseApplication).database.contactDao()
        )
    }

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var contact: Contact

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.contactId

        if (id > 0) {

            viewModel.retrieveContact(id).observe(this.viewLifecycleOwner) {
                contact = it
                bindContact(contact)
            }
        } else {
            binding.contactDetailSaveButton.setOnClickListener { addContact() }
        }
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
                id = navigationArgs.contactId,
                name = binding.contactDetailName.text.toString(),
                surname = binding.contactDetailSurname.text.toString(),
                phoneNumber = binding.contactDetailPhoneNumber.text.toString()
            )

            val action = ContactDetailFragmentDirections
                .actionContactDetailFragmentToContactsListFragment()

            findNavController().navigate(action)
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

            val action = ContactDetailFragmentDirections
                .actionContactDetailFragmentToContactsListFragment()

            findNavController().navigate(action)
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