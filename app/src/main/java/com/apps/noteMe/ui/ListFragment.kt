package com.apps.noteMe.ui


import android.app.AlertDialog
import android.os.Bundle
import android.transition.Scene
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.noteMe.R
import com.apps.noteMe.adatper.NoteListener
import com.apps.noteMe.adatper.RVAdapter
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.AddNewListLayoutBinding
import com.apps.noteMe.databinding.FragmentListBinding
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SharedViewModel
import com.apps.noteMe.sharedViewModels.SharedViewModelFactory
import kotlin.math.hypot

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), NoteListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var dialogBinding: AddNewListLayoutBinding
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DAOs.noteDao = AppDatabase.getInstance(context!!).noteDao
        DAOs.noteListDao = AppDatabase.getInstance(context!!).noteListDao
        DAOs.userIdDao = AppDatabase.getInstance(context!!).userIdDao
        viewModel = ViewModelProviders.of(
                this,
                SharedViewModelFactory(Repos.noteRepository, Repos.noteListRepository)
            )
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
            this.findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(0))
        }

//        binding.notesRecyclerView.setOnClickListener {
////            val dialog = AlertDialog.Builder(context).setMessage("This is a Dialog")
////                .create()
////            dialog.window?.setWindowAnimations(R.style.PauseDialogAnimation)
//            val cx = binding.fab.width / 2
//            val cy = binding.fab.height / 2
//
//            // get the final radius for the clipping circle
//            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
//
//            // create the animator for this view (the start radius is zero)
//            val anim = ViewAnimationUtils.createCircularReveal(binding.fab, cx, cy, 0f, finalRadius)
//            // make the view visible and start the animation
//            binding.fab.visibility = View.VISIBLE
//            anim.start()
////            dialog.show()
////            findNavController().navigate(
////                ListFragmentDirections.actionListFragmentToNoteFragment(
////                    0
////                )
////            )
//        }

//        binding.navView.menu.findItem(R.id.add_list_item).setOnMenuItemClickListener {
//
//            binding.drawerLayout.closeDrawer(binding.navView, true)
//
//            viewModel.noteList.value = NoteList(title = "")
//
//            dialogBinding = AddNewListLayoutBinding.inflate(layoutInflater)
//            dialogBinding.viewModel = viewModel
//
//
//            val dialog = AlertDialog.Builder(context).setView(dialogBinding.root).create()
//            dialog.show()
//
//
//            dialogBinding.btnOk.setOnClickListener {
//                binding.navView.menu.add(viewModel.noteList.value?.title).icon =
//                    resources.getDrawable(R.drawable.ic_format_list_bulleted_black_24dp, null)
//                viewModel.insertNoteList(viewModel.noteList.value!!)
//                dialog.cancel()
//            }
//
//            true
//        }


        return binding.root
    }

    override fun onNoteClick(id: Long) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToNoteFragment(id))
    }
}
