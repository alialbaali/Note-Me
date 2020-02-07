package com.apps.noteMe.signInUI


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apps.noteMe.R
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    // binding variable
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflating the layout using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        // getting the data from the Dao
        val dataSource = AppDatabase.getInstance(this.activity!!).userDao
        // passing the data to the View Model Factory
        val signInViewModelFactory = SignInViewModelFactory(dataSource)
        // initializing the View Model
        val signInViewModel = ViewModelProviders.of(this, signInViewModelFactory).get(SignInViewModel::class.java)
        // connecting the View Model with data binding
        binding.signInViewModel = signInViewModel
        // setting lifecycle owner
        binding.lifecycleOwner = this




        return binding.root
    }
}
