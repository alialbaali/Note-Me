package com.apps.noteMe.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.apps.noteMe.R
import com.apps.noteMe.database.AppDatabase
import com.apps.noteMe.databinding.FragmentSignInBinding
import com.apps.noteMe.network.DAOs
import com.apps.noteMe.network.Repos
import com.apps.noteMe.sharedViewModels.SignNavigation
import com.apps.noteMe.sharedViewModels.SignSharedViewModel
import com.apps.noteMe.sharedViewModels.SignSharedViewModelFactory


class SignInFragment : Fragment(), SignNavigation {

    private lateinit var binding: FragmentSignInBinding

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


        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.signNavigation = this

        binding.btnSignIn.setOnClickListener {
            viewModel.signIn()
        }

        binding.tvSignUp.setOnClickListener {
            it.findNavController()
                .navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun navigate() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signInFragment, true)
            .build()
        this.findNavController()
            .navigate(SignInFragmentDirections.actionGlobalListFragment(), navOptions)
    }
}
