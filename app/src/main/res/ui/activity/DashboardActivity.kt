package com.example.firebaselearn.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.firebaselearn.R
import com.example.firebaselearn.databinding.ActivityDashboardBinding
import com.example.firebaselearn.ui.fragment.FirstFragment
import com.example.firebaselearn.ui.fragment.SecondFragment
import com.example.firebaselearn.ui.fragment.ProfileFragment
import com.example.firebaselearn.utils.LoadingUtils

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottoView -> bottomnavigationview ko id

        replaceFragment(FirstFragment())

        binding.bottomView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> replaceFragment(FirstFragment())
                R.id.navSearch -> replaceFragment(SecondFragment())
                R.id.navProfile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager =
            supportFragmentManager

        val fragmentTransaction: FragmentTransaction =
            fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameBottom, fragment)
        fragmentTransaction.commit()
    }
}