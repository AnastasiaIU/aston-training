package com.example.astontraining.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.astontraining.BaseApplication
import com.example.astontraining.data.Contact
import com.example.astontraining.databinding.FragmentContactsListBinding
import com.example.astontraining.viewmodel.ContactsViewModel
import com.example.astontraining.viewmodel.ContactsViewModelFactory

/**
 * The fragment with the list of Contacts stored in the database.
 *
 * Clicking on a list item or the FloatingActionButton launches the ContactDetailFragment.
 */
class ContactsListFragment : Fragment() {

    private val viewModel: ContactsViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as BaseApplication).database.contactDao()
        )
    }

    private var _binding: FragmentContactsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toContactDetail: (Int) -> Unit = { id ->

            val action = ContactsListFragmentDirections
                .actionContactsListFragmentToContactDetailFragment(id)

            this.findNavController().navigate(action)
        }

        val deleteContact: (Contact) -> Boolean = { contact ->

            viewModel.deleteContact(contact)

            true
        }

        val adapter = ContactsListAdapter(toContactDetail, deleteContact)

        // Attach an observer on the contacts list to update
        // the UI automatically when the data changes
        viewModel.contacts.observe(this.viewLifecycleOwner) { contacts ->
            contacts.let { adapter.submitList(it) }
        }

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

            floatingActionButton.setOnClickListener {

                /*val data = DataSource()

                for (i in 50..109) {
                    viewModel.addNewContact(
                        data.namesList[i],
                        data.surnamesList[i],
                        data.phoneNumbersList[i]
                    )
                }*/

                val action = ContactsListFragmentDirections
                    .actionContactsListFragmentToContactDetailFragment(0)

                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}