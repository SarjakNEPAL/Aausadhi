package com.example.aausadhi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aausadhi.R
import com.example.aausadhi.databinding.ActivityRegisterBinding
import com.example.aausadhi.repository.UserRepositoryImpl
import com.example.aausadhi.model.UserModel
import com.example.aausadhi.utils.LoadingUtils
import com.example.aausadhi.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this@RegisterActivity)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.loginHREF.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.submitButtonReg.setOnClickListener {
            if (validateForm()) {
                loadingUtils.show()
                val email: String = binding.editEmailReg.text.toString()
                val password: String = binding.passwordReg.text.toString()
                userViewModel.signup(email, password) { success, messege, userid ->
                    if (success) {
                        val userModel = UserModel(
                            userid,
                            binding.NameReg.text.toString(),
                            binding.phoneReg.text.toString(),
                            binding.addrReg.text.toString(),
                        )
                        addUser(userModel)
                    } else {
                        Toast.makeText(this@RegisterActivity, messege, Toast.LENGTH_SHORT).show()
                        loadingUtils.dismiss()
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val name = binding.NameReg.text.toString().trim()
        val email = binding.editEmailReg.text.toString().trim()
        val phone = binding.phoneReg.text.toString().trim()
        val address = binding.addrReg.text.toString().trim()
        val password = binding.passwordReg.text.toString().trim()

        if (name.isEmpty()) {
            binding.NameReg.error = "Name is required"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.editEmailReg.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmailReg.error = "Invalid email format"
            isValid = false
        }

        if (phone.isEmpty()) {
            binding.phoneReg.error = "Phone number is required"
            isValid = false
        }

        if (address.isEmpty()) {
            binding.addrReg.error = "Address is required"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordReg.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordReg.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId, userModel) { success, message ->
            if (success) {
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                finish()
                } else {
                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
            }
            loadingUtils.dismiss()
        }
    }
}
