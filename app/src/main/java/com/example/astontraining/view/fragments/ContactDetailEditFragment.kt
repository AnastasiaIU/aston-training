package com.example.astontraining.view.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.astontraining.databinding.FragmentContactDetailBinding

/**
 * A fragment representing a single Contact detail screen.
 * This fragment is either contained in a [ContactListFragmentDelete]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class ContactDetailEditFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}