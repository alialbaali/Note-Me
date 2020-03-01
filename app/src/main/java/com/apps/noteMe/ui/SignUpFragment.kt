package com.apps.noteMe.ui


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentSignUpBinding
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SignNavigation
import com.apps.noteMe.sharedViewModels.SignSharedViewModel
import com.apps.noteMe.sharedViewModels.SignSharedViewModelFactory
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(), SignNavigation {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DAOs.userIdDao = AppDatabase.getInstance(context!!).userIdDao
        viewModel = ViewModelProviders.of(this, SignSharedViewModelFactory(Repos.userRepository))
            .get(SignSharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.signNavigation = this

        binding.lifecycleOwner = this

        binding.btnSignUp.setOnClickListener {
            viewModel.signUp()
        }

        binding.tvSignIn.setOnClickListener {
            it.findNavController()
                .navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        return binding.root
    }

    override fun navigate() {
        this.findNavController().navigate(SignUpFragmentDirections.actionGlobalListFragment())
    }
}
