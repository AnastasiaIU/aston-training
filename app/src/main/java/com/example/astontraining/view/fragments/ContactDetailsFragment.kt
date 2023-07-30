package com.example.astontraining.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import com.example.astontraining.R
import com.example.astontraining.databinding.FragmentContactDetailsBinding
import com.example.astontraining.model.Contact
import com.example.astontraining.view.Utility
import com.example.astontraining.viewmodel.ContactsViewModel
import javax.inject.Inject

/**
 * This fragment represents a [Contact] Detail screen.
 *
 * This fragment is either contained in a [ContactsSlidingPaneFragment]
 * in two-pane mode (on larger screen devices) or self-contained on handsets.
 */
class ContactDetailsFragment @Inject constructor() : BaseFragment() {

    private lateinit var binding: FragmentContactDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        setFragmentRootVisibility()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Attaches an observer on a current contact to update
        // the UI automatically when the data changes.
        viewModel.currentContact.observe(this.viewLifecycleOwner) { contact ->

            if (contact != null) bindContact(contact)

            setFragmentRootVisibility()
        }

        // Sets OnClickListener on Edit FAB.
        binding.editFab.setOnClickListener {

            it.findNavController().navigate(
                ContactsSlidingPaneFragmentDirections.actionSlidingPaneToEditContactFragment()
            )
        }
    }

    /**
     * Binds provided [contact] to [ContactDetailsFragment].
     */
    private fun bindContact(contact: Contact) {

        binding.apply {
            contactDetailFullName.text = Utility.formatToFullName(contact.name, contact.surname)
            contactDetailPhoneNumber.text = Utility.formatPhoneNumber(contact.phoneNumber)
        }

        val defaultDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.no_profile_picture_icon,
            requireActivity().theme
        )

        Utility.loadImage(
            requireContext(),
            contact.imageUrl,
            binding.listItemContactImage,
            defaultDrawable
        )
    }

    /**
     * Sets [ContactDetailsFragment] root to be visible if a current contact is assigned
     * at [ContactsViewModel], otherwise sets it to be invisible.
     */
    private fun setFragmentRootVisibility() {

        binding.root.visibility = if (viewModel.currentContact.value == null) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }
}