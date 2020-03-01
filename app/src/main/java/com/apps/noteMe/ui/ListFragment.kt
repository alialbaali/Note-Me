package com.apps.noteMe.ui


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.noteMe.R
import com.apps.noteMe.adatper.ItemTouchHelperAdapter
import com.apps.noteMe.adatper.NoteListener
import com.apps.noteMe.adatper.RVAdapter
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentListBinding
import com.apps.noteMe.model.Note
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), NoteListener {

    private lateinit var viewModel: SharedViewModel

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: RVAdapter
    private lateinit var itemTouchHelperAdapter: ItemTouchHelperAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DAOs.noteDao = AppDatabase.getInstance(context!!).noteDao
        viewModel = ViewModelProviders.of(this, SharedViewModelFactory(Repos.noteRepository))
            .get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        binding.lifecycleOwner = this

        adapter = RVAdapter(this)

        binding.notesRecyclerView.adapter = adapter

        binding.notesRecyclerView.setHasFixedSize(true)

        itemTouchHelperAdapter =
            ItemTouchHelperAdapter(adapter)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperAdapter)
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)

        binding.notesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//        binding.notesRecyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toMutableList())
            }
        })

        adapter.submitList(listOf())

        binding.notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.rv_item_decorater_shape
                    )!!
                )
            })


        binding.fab.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToNoteFragment(
                    0
                )
            )
        }



        return binding.root
    }


    override fun onNoteClick(id: Long) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(id))
    }

    override fun onNoteMove(fromPosition: Int, toPosition: Int) {


        viewModel.notes.value?.let {
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    it[i] = it.set(i + 1, it[i])
                }
            } else {
                for (i in fromPosition..toPosition + 1) {
                    it[i] = it.set(i - 1, it[i])
                }
            }
        }
    }

    override fun onNoteSwipe(note: Note) {
        viewModel.delete(note)
        Snackbar.make(
            binding.coordinatorLayout,
            R.string.note_deleted_message,
            Snackbar.LENGTH_LONG
        ).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            show()
        }
    }


}
