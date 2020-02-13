package com.apps.noteMe.noteUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apps.noteMe.R
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentNoteBinding
import com.google.android.material.snackbar.Snackbar


class NoteFragment : Fragment() {


    private lateinit var viewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).noteDao

        val viewModelFactory =
            SharedViewModelFactory(dataSource)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel::class.java)

        // Implement custom onBackPressed action
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToListFragment())
            viewModel.save()
        }.isEnabled = true

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note, container, false
        )
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.toolbar.inflateMenu(R.menu.note_menu)

        val id = requireArguments().getLong("id")

        viewModel.getNoteById(id)

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
            viewModel.save()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToListFragment())
                    viewModel.delete(null)
                    Snackbar.make(view!!, R.string.note_deleted_message, Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
        return binding.root
    }

}