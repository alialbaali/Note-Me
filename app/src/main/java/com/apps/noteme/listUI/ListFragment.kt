package com.apps.noteme.listUI


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apps.noteme.R
import com.apps.noteme.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_list, container, false)

        binding.fab.setOnClickListener {
            //TODO Handle Click Even by Creating New Note
        }
        return binding.root
    }


}
