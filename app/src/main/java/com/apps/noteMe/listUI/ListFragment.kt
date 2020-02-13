package com.apps.noteMe.listUI


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
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.database.NoteDao
import com.apps.noteMe.databinding.FragmentListBinding
import com.apps.noteMe.models.Note

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), NoteListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: RVAdapter
    private lateinit var dataSource: NoteDao
    private lateinit var itemTouchHelperAdapter: ItemTouchHelperAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        dataSource = AppDatabase.getInstance(application).noteDao

        val viewModelFactory =
            SharedViewModelFactory(dataSource)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        binding.lifecycleOwner = this

        binding.toolbar.inflateMenu(R.menu.list_menu)

        adapter = RVAdapter(this)

        binding.notesRecyclerView.adapter = adapter

        binding.notesRecyclerView.setHasFixedSize(true)

        itemTouchHelperAdapter = ItemTouchHelperAdapter(adapter)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperAdapter)
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)

        binding.notesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toMutableList())
            }
        })

        binding.notesRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            setDrawable(ContextCompat.getDrawable(context!!, R.drawable.rv_item_decorater_shape)!!)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(0))
        }

        return binding.root
    }

    override fun onNoteClick(id: Long) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(id))
    }

    override fun onNoteMove(fromPosition: Int, toPosition: Int) {

    }

    override fun onNoteSwipe(note:Note) {
        viewModel.delete(note)
    }
}
