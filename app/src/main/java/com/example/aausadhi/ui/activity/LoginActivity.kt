package com.example.pharmabucket.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pharmabucket.R
import com.example.pharmabucket.databinding.ActivityLoginBinding
import com.example.pharmabucket.repository.UserRepositoryImpl
import com.example.pharmabucket.utils.LoadingUtils
import com.example.pharmabucket.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loadingUtils: LoadingUtils

    private lateinit var userViewModel: UserViewModel

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        loadingUtils=LoadingUtils(this@LoginActivity)
        sharedPreferences=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)
        setContentView(binding.root)
        binding.RegisterLogin.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.submitButton.setOnClickListener{
            val email=binding.editEmailLogin.text.toString()
            val password= binding.loginPasswordEntry.text.toString()
            login(email,password)
        }
        binding.forgotPassword.setOnClickListener{
            loadingUtils.show()
            val email=binding.editEmailLogin.text.toString()
            userViewModel.forgetPassword(email){
                success, message->
                if (success) {
                    Toast.makeText(
                        this, "$message Redirecting To Login Page !", Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this,LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this, message, Toast.LENGTH_SHORT
                    ).show()
                }
                loadingUtils.dismiss()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun login(email: String, password: String){
        userViewModel.login(email,password){
            success, message->
            if(success){
                Toast.makeText(
                    this@LoginActivity, message, Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this@LoginActivity,ProductDashboardActivity::class.java))
            }
            Toast.makeText(
                this@LoginActivity, message, Toast.LENGTH_SHORT
            ).show()

        }
    }
}