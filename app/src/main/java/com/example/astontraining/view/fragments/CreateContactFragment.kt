package com.example.astontraining.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.astontraining.R
import com.example.astontraining.databinding.FragmentCreateContactBinding
import com.example.astontraining.model.Contact
import com.example.astontraining.view.Utility
import javax.inject.Inject

/**
 * This fragment represents a Create [Contact] screen.
 */
class CreateContactFragment @Inject constructor() : BaseFragment() {

    private lateinit var binding: FragmentCreateContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        binding = FragmentCreateContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sets OnClickListener on Create button.
        binding.createButton.setOnClickListener {

            if (
                Utility.isValidEntry(
                    binding.createFieldNameText.text.toString(),
                    binding.createFieldSurnameText.text.toString(),
                    binding.createFieldPhoneNumberText.text.toString()
                )
            ) {

                createContact()

                Utility.showShortToast(requireContext(), getString(R.string.toast_created))

                requireActivity().onBackPressedDispatcher.onBackPressed()

            } else {

                Utility.showShortToast(requireContext(), getString(R.string.toast_wrong_input))
            }
        }
    }

    /**
     * Creates a new [Contact] in local database.
     */
    private fun createContact() {

        val contact = Contact(
            name = binding.createFieldNameText.text.toString(),
            surname = binding.createFieldSurnameText.text.toString(),
            phoneNumber = binding.createFieldPhoneNumberText.text.toString().toLong(),
            imageUrl = binding.createFieldImageText.text.toString()
        )

        viewModel.addNewContact(contact)
    }
}