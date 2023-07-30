package com.example.astontraining.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.astontraining.R
import com.example.astontraining.databinding.FragmentContactsSlidingPaneBinding
import com.example.astontraining.model.Contact
import com.example.astontraining.view.Utility
import com.example.astontraining.view.ViewConstants.SEARCH_FIELD_DELAY
import com.example.astontraining.view.ViewConstants.SOFT_INPUT_NO_FLAGS
import com.example.astontraining.view.adapter.ContactsListAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * This fragment displays a list of [Contact]s.
 *
 * It has different presentations for handset and larger screen devices.
 * On handsets, the fragment presents a list of items, which when touched,
 * lead to a [ContactDetailsFragment] representing item details.
 * On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ContactsSlidingPaneFragment @Inject constructor() : BaseFragment(),
    ContactsListAdapter.AdapterInterface {

    private lateinit var binding: FragmentContactsSlidingPaneBinding

    private lateinit var contactsListAdapter: ContactsListAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment.
        binding = FragmentContactsSlidingPaneBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ItemDecoration divider.
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }

        contactsListAdapter = ContactsListAdapter(this as ContactsListAdapter.AdapterInterface)

        // Adds a custom back navigation callback.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            ContactsListOnBackPressedCallback(binding.slidingPaneLayout)
        )

        // Attaches an observer on a contacts list to update
        // the UI automatically when the data changes.
        viewModel.contacts.observe(this.viewLifecycleOwner) { contacts ->
            contacts.let { contactsListAdapter.submitList(it) }
        }

        // Sets up RecyclerView.
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        // Sets OnClickListener on Create Contact button.
        binding.createContactButton.setOnClickListener {
            it.findNavController().navigate(
                ContactsSlidingPaneFragmentDirections.actionSlidingPaneToCreateContactFragment()
            )
        }

        // Sets an observer for a search query.
        observeSearchQuery()

        // Sets behavior for a search field.
        setSearchFieldBehavior()

        // Sets a text change listener at the search field.
        addSearchQuery()
    }

    /**
     * Adds a search query and results of it with a delay
     * to the view model when text is changed at the search field.
     */
    private fun addSearchQuery() {

        binding.searchEditText.addTextChangedListener { editable ->

            val text = editable.toString().trim()

            searchJob?.cancel()

            searchJob = MainScope().launch(Dispatchers.IO) {

                delay(SEARCH_FIELD_DELAY)

                viewModel.apply {
                    searchQuery.postValue(text)
                    search(text)
                }
            }
        }
    }

    /**
     * Sets observer to the search query to show results of the search or the full list of houses.
     */
    private fun observeSearchQuery() {

        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->

            if (query.isNotBlank()) {

                viewModel.apply {
                    contacts.removeObservers(viewLifecycleOwner)
                    searchResults.observe(viewLifecycleOwner) { list ->
                        contactsListAdapter.submitList(list)
                    }
                }

            } else {

                viewModel.apply {
                    searchResults.removeObservers(viewLifecycleOwner)
                    contacts.observe(viewLifecycleOwner) { list ->
                        contactsListAdapter.submitList(list)
                    }
                }

            }
        }
    }

    /**
     * Sets the trailing icon and it's behavior at the search field.
     */
    private fun setSearchFieldBehavior() {

        val inputMethodManager = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchEditText.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus || (v as TextInputEditText).text!!.isNotEmpty()) {

                binding.searchLayout.apply {

                    // Sets a close icon.
                    setEndIconDrawable(R.drawable.baseline_close_24)

                    // Sets OnClickListener on the icon.
                    setEndIconOnClickListener {

                        (v as TextInputEditText).apply {
                            text!!.clear()
                            clearFocus()
                        }

                        // Hides keyboard.
                        inputMethodManager.hideSoftInputFromWindow(
                            binding.root.windowToken, SOFT_INPUT_NO_FLAGS
                        )
                    }
                }

            } else {

                binding.searchLayout.apply {

                    // Set a search icon.
                    setEndIconDrawable(R.drawable.baseline_search_24)

                    // Sets OnClickListener on the icon.
                    setEndIconOnClickListener {

                        v.requestFocus()

                        // Shows keyboard.
                        inputMethodManager.showSoftInput(v, SOFT_INPUT_NO_FLAGS)
                    }
                }
            }
        }
    }

    /**
     * Custom back navigation callback.
     */
    inner class ContactsListOnBackPressedCallback(
        private val slidingPaneLayout: SlidingPaneLayout
    ) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
        SlidingPaneLayout.PanelSlideListener {

        override fun handleOnBackPressed() {
            slidingPaneLayout.closePane()
        }

        override fun onPanelSlide(panel: View, slideOffset: Float) {}

        override fun onPanelOpened(panel: View) {
            isEnabled = true
        }

        override fun onPanelClosed(panel: View) {
            isEnabled = false
        }

        init {
            slidingPaneLayout.addPanelSlideListener(this)
        }
    }

    /**
     * Navigates to [ContactDetailsFragment] displaying provided [contact].
     */
    override fun openContactDetails(contact: Contact) {

        viewModel.currentContact.postValue(contact)

        binding.slidingPaneLayout.openPane()
    }

    /**
     * Deletes provided [contact] from local database.
     */
    override fun deleteContact(contact: Contact): Boolean {

        viewModel.deleteContact(contact)

        return true
    }

    /**
     * Loads provided [contact] image and sets it to provided [imageView].
     *
     * Sets a default drawable to provided [imageView] if an error occurred during the loading.
     */
    override fun loadContactImage(contact: Contact, imageView: ImageView) {

        val defaultDrawable = ResourcesCompat.getDrawable(
            requireActivity().resources,
            R.drawable.no_profile_picture_icon,
            requireActivity().theme
        )

        Utility.loadImage(
            requireContext(),
            contact.imageUrl,
            imageView,
            defaultDrawable
        )
    }
}