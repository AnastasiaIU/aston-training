package com.example.astontraining.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.astontraining.ContactsApplication
import com.example.astontraining.R
import com.example.astontraining.view.adapter.ContactsListAdapter
import com.example.astontraining.model.Contact
import com.example.astontraining.databinding.FragmentContactsSlidingPaneBinding
import com.example.astontraining.viewmodel.ContactsViewModel
import com.example.astontraining.viewmodel.ContactsViewModelFactory

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ContactDetailFragmentDelete} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
/**
 * The fragment with the list of Contacts stored in the database.
 *
 * Clicking on a list item or the FloatingActionButton launches the ContactDetailFragmentDelete.
 */
class ContactsSlidingPaneFragment : Fragment() {

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    /*private val unhandledKeyEventListenerCompat =
        ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
            if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
                Toast.makeText(
                    v.context,
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
                ).show()
                true
            }
            false
        }*/

    private val viewModel: ContactsViewModel by activityViewModels {
        ContactsViewModelFactory(
            (requireActivity().application as ContactsApplication).repository
        )
    }

    private var _binding: FragmentContactsSlidingPaneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContactsSlidingPaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val contactDetailFragmentContainer: View? =
            view.findViewById(R.id.nav_host_fragment)

        val toContactDetail: (Int) -> Unit = { contactId ->

//            val bundle = bundleOf(ContactDetailFragmentDelete.ARG_CONTACT_ID to contactId)

            /*if (contactDetailFragmentContainer != null) {
                contactDetailFragmentContainer
                    .findNavController()
                    .navigate(R.id.action_contacts_list_fragment_to_contact_detail_fragment, bundle)
            } else {
                this.findNavController().navigate(R.id.action_contacts_list_fragment_to_contact_detail_fragment, bundle)
            }*/

            /*val action = ContactsListFragmentDirections
                .showContactDetail(id)*/

            //val bundle = bundleOf(ContactDetailFragmentDelete.ARG_CONTACT_ID to contactId)

            //this.findNavController().navigate(action)

            binding.slidingPaneLayout.openPane()
        }

        val deleteContact: (Contact) -> Boolean = { contact ->

            viewModel.deleteContact(contact)

            true
        }

        val adapter = ContactsListAdapter(toContactDetail, deleteContact)

        // Attach an observer on the contacts list to update
        // the UI automatically when the data changes
        /*viewModel.contacts.observe(this.viewLifecycleOwner) { contacts ->
            contacts.let { adapter.submitList(it) }
        }*/

        /*binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}