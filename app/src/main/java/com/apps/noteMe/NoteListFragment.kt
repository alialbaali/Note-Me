package com.apps.noteMe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.noteMe.adatper.NoteListRVAdapter
import com.apps.noteMe.databinding.FragmentNoteListBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteListFragment : Fragment() {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NoteListRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this

        viewModel = NoteListViewModel()

        adapter = NoteListRVAdapter()

        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}
