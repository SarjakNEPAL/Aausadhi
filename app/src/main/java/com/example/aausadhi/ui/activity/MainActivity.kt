package com.example.aausadhi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aausadhi.R
import com.example.aausadhi.databinding.ActivityMainBinding
import com.example.aausadhi.repository.UserRepositoryImpl
import com.example.aausadhi.utils.LocalStorage
import com.example.aausadhi.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LocalStorage.init(this)
        userViewModel = UserViewModel(UserRepositoryImpl())

        Handler(Looper.getMainLooper()).postDelayed({
            checkStoredCredentials()
        }, 3000)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkStoredCredentials() {
        val (email, password) = LocalStorage.fetchUserCredentials()

        if (email != null && password != null) {
            // Attempt automatic login
            userViewModel.login(email, password) { success, _ ->
                if (success) {
                                val intent = Intent(this@MainActivity, ProductDashboardActivity::class.java)
                                startActivity(intent)
                                finish()

                } else {
                    redirectToStartActivity()
                }
            }
        } else {
            // No stored credentials, redirect to StartActivity
            redirectToStartActivity()
        }
    }

    private fun redirectToStartActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
