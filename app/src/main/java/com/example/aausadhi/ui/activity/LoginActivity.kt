package com.example.aausadhi.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aausadhi.R
import com.example.aausadhi.databinding.ActivityLoginBinding
import com.example.aausadhi.repository.UserRepositoryImpl
import com.example.aausadhi.utils.LoadingUtils
import com.example.aausadhi.utils.LocalStorage
import com.example.aausadhi.viewmodel.UserViewModel
import com.google.firebase.database.core.view.View

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loadingUtils: LoadingUtils

    private lateinit var userViewModel: UserViewModel

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        loadingUtils= LoadingUtils(this@LoginActivity)
        LocalStorage.init(this)
        sharedPreferences=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)
        setContentView(binding.root)
        binding.registerLink.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.submitButton.setOnClickListener{
            val email=binding.editEmailLogin.text.toString()
            val password= binding.loginPasswordEntry.text.toString()
            login(email,password)
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun login(email: String, password: String) {
        if (email.isEmpty()) {
            binding.editEmailLogin.error = "Email is required"
            binding.editEmailLogin.requestFocus()
            return
        }

        if (password.isEmpty()) {

            binding.loginPasswordEntry.error = "Password is required"
            binding.loginPasswordEntry.requestFocus()
            return
        }
        loginUser(email, password)
    }
    private fun loginUser(email: String, password: String) {
        userViewModel.login(email, password) { success, message ->
            if (success) {
                userId = userViewModel.getCurrentUser()?.uid.toString()
                Toast.makeText(this@LoginActivity, "Logged in", Toast.LENGTH_SHORT).show()
                // Store user credentials using LocalStorage
                LocalStorage.storeUserCredentials( email,password)
                val intent = Intent(this@LoginActivity, ProductDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                binding.resultText.text="Login failed"
                binding.resultText.visibility=android.view.View.GONE
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}