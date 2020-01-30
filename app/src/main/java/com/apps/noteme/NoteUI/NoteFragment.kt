package com.apps.noteme.NoteUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apps.noteme.R
import com.apps.noteme.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_note, container, false)

        // TODO Handle Saved Note Data
        // TODO Create Save Button and Implement a Click Listener


        return binding.root
    }
}