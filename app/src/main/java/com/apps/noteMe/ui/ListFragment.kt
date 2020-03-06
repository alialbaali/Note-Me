package com.apps.noteMe.ui


import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.noteMe.R
import com.apps.noteMe.adatper.NoteListener
import com.apps.noteMe.adatper.RVAdapter
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentListBinding
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), NoteListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DAOs.noteDao = AppDatabase.getInstance(context!!).noteDao
        DAOs.userIdDao = AppDatabase.getInstance(context!!).userIdDao
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

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView, true)
        }

        binding.notesRecyclerView.setHasFixedSize(true)


        binding.notesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//        binding.notesRecyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

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
}
