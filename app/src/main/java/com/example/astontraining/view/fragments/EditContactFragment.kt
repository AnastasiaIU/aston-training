package com.example.astontraining.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astontraining.R
import com.example.astontraining.databinding.FragmentEditContactBinding
import com.example.astontraining.model.Contact
import com.example.astontraining.view.Utility
import javax.inject.Inject

/**
 * This fragment represents a Edit [Contact] screen.
 */
class EditContactFragment @Inject constructor() : BaseFragment() {

    private lateinit var binding: FragmentEditContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        binding = FragmentEditContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContact()

        // Sets OnClickListener on Save button.
        binding.saveButton.setOnClickListener {

            if (
                Utility.isValidEntry(
                    binding.editFieldNameText.text.toString(),
                    binding.editFieldSurnameText.text.toString(),
                    binding.editFieldPhoneNumberText.text.toString()
                )
            ) {

                updateContact()

                Utility.showShortToast(requireContext(), getString(R.string.toast_saved))

                requireActivity().onBackPressedDispatcher.onBackPressed()

            } else {

                Utility.showShortToast(requireContext(), getString(R.string.toast_wrong_input))
            }
        }
    }

    /**
     * Sets current contact data from ViewModel to the Edit [Contact] fragment.
     */
    private fun setContact() {

        viewModel.currentContact.value!!.apply {
            binding.apply {
                editFieldNameText.setText(name)
                editFieldSurnameText.setText(surname)
                editFieldPhoneNumberText.setText(phoneNumber.toString())
                editFieldImageText.setText(imageUrl)
            }
        }
    }

    /**
     * Updates the current contact in a local database.
     */
    private fun updateContact() {

        val contact = Contact(
            viewModel.currentContact.value!!.id,
            binding.editFieldNameText.text.toString(),
            binding.editFieldSurnameText.text.toString(),
            binding.editFieldPhoneNumberText.text.toString().toLong(),
            binding.editFieldImageText.text.toString()
        )

        viewModel.updateContact(contact)
    }
}