package com.example.astontraining.view.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.astontraining.appComponent
import com.example.astontraining.viewmodel.ContactsViewModel
import javax.inject.Inject

/**
 * This class contains a common base for fragments.
 */
open class BaseFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var contactsViewModelFactory: dagger.Lazy<ContactsViewModel.Factory>

    /**
     * Shared ViewModel.
     */
    val viewModel: ContactsViewModel by activityViewModels { contactsViewModelFactory.get() }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)

        super.onAttach(context)
    }
}