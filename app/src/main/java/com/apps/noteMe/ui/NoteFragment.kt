package com.apps.noteMe.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apps.noteMe.R
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentNoteBinding
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding


    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DAOs.noteDao = AppDatabase.getInstance(context!!).noteDao
        DAOs.noteListDao = AppDatabase.getInstance(context!!).noteListDao
        viewModel = ViewModelProviders.of(this, SharedViewModelFactory(Repos.noteRepository, Repos.noteListRepository))
            .get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        val id = requireArguments().getLong("id")

        viewModel.getNoteById(id)

        // Implement custom onBackPressed action
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToListFragment())
            viewModel.save()
        }.isEnabled = true

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
            viewModel.save()
        }

        viewModel.currentNote.observe(viewLifecycleOwner, Observer {
            Timber.i(it.toString())
        })



        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    findNavController().navigate(NoteFragmentDirections.actionNoteFragmentToListFragment())
                    viewModel.delete(null)

                    Snackbar.make(view!!, R.string.note_deleted_message, Snackbar.LENGTH_LONG)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()

                    true

                }
                R.id.share -> {
                    if (shareIntent().resolveActivity(activity!!.packageManager) == null) {
                        binding.toolbar.menu.findItem(R.id.share).isVisible = false
                    } else {
                        startActivity(
                            Intent.createChooser(
                                shareIntent(),
                                resources.getString(R.string.share_title)
                            )
                        )
                    }
                    true
                }
                else -> true
            }
        }
        return binding.root
    }

    private fun shareIntent(): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT, """
                |${viewModel.currentNote.value?.title}
                |${viewModel.currentNote.value?.content}
            """.trimMargin()
            )
        }
    }
}