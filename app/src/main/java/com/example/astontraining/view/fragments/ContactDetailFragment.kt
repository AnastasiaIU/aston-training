package com.example.astontraining.view.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.astontraining.R
import com.example.astontraining.databinding.FragmentContactDetailBinding
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a [ContactListFragmentDelete]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ContactDetailFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentContactDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
        }
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

//        toolbarLayout = binding.toolbarLayout
        //itemDetailTextView = binding.contactDetail

//        updateContent()
//        rootView.setOnDragListener(dragListener)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // The usage of an interface lets you inject your own implementation.
//        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs.
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible.
        /*menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.contact_detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                // Handle the menu selection
                view.findNavController().navigate(R.id.action_contact_detail_fragment_to_contact_detail_edit_fragment)

                return true
            }
        }, viewLifecycleOwner)*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}