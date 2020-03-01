package com.apps.noteMe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apps.noteMe.databinding.ActivityMainBinding
import com.apps.noteMe.network.Repos

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
//
//    override fun onStart() {
//        super.onStart()
//        intent?.extras?.getString(Intent.EXTRA_TEXT)?.let {
//            Timber.i(it)
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment_container, NoteFragment(), null)
//                .addToBackStack(null)
//                .commit()
//        }
//    }
}
