package com.example.aausadhi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aausadhi.databinding.ActivityUpdateProfileBinding
import com.example.aausadhi.model.UserModel
import com.example.aausadhi.repository.UserRepositoryImpl
import com.example.aausadhi.viewmodel.UserViewModel

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var data: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = UserViewModel(UserRepositoryImpl())

        // Get the current user ID
        val userID = userViewModel.getCurrentUser()?.uid

        userID?.let {
            userViewModel.getDataFromDB(it)
        }

        // Observe the user data
        userViewModel.userData.observe(this) { userModel ->
            userModel?.let {
                data = UserModel(it.userId, it.fullName, it.phoneNumber, it.address)
                // Set the text here, where the data is initialized
                binding.upNameInput.setText(data.fullName)
                binding.upPhoneInput.setText(data.phoneNumber)
                binding.upLocationInput.setText(data.address)
            }
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.upsubmitProfile.setOnClickListener {
            if (validateForm()) {
                val name = binding.upNameInput.text.toString().trim()
                val phone = binding.upPhoneInput.text.toString().trim()
                val location = binding.upLocationInput.text.toString().trim()

                val updatedUser = mutableMapOf<String, Any>()
                updatedUser["userID"] = data.userId
                updatedUser["fullName"] = name
                updatedUser["phoneNumber"] = phone
                updatedUser["address"] = location

                userID?.let {
                    userViewModel.editProfile(it, updatedUser) { success, message ->
                        if (success) {
                            Toast.makeText(this@UpdateProfileActivity, message, Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@UpdateProfileActivity, message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val name = binding.upNameInput.text.toString().trim()
        val phone = binding.upPhoneInput.text.toString().trim()
        val location = binding.upLocationInput.text.toString().trim()

        if (name.isEmpty()) {
            binding.upNameInput.error = "Full name is required"
            isValid = false
        }

        if (phone.isEmpty()) {
            binding.upPhoneInput.error = "Phone number is required"
            isValid = false
        }

        if (location.isEmpty()) {
            binding.upLocationInput.error = "Location is required"
            isValid = false
        }

        return isValid
    }
}
