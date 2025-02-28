package com.example.aausadhi.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.aausadhi.databinding.ActivityProductDashboardBinding
import com.example.aausadhi.R
import com.example.aausadhi.ui.fragment.FirstFragment
import com.example.aausadhi.ui.fragment.SecondFragment
import com.example.aausadhi.ui.fragment.ProfileFragment


class ProductDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDashboardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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