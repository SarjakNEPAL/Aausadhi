package com.example.aausadhi.ui.activity

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aausadhi.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.sendEmailButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (isValidEmail(email)) {
                sendPasswordResetEmail(email)
            } else {
                binding.emailInput.error = "Invalid email address"
            }
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
