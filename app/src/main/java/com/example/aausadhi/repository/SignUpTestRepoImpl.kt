package com.example.aausadhi.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUpTestRepoImpl(private val firebaseAuth: FirebaseAuth) {

    fun signup(email: String, password: String, callback: (Boolean, String?, String) -> Unit) {
        if (firebaseAuth == null) {
            callback(false, "FirebaseAuth instance is null", "")
            return
        }

        val task = firebaseAuth.createUserWithEmailAndPassword(email, password)
        task?.addOnCompleteListener(OnCompleteListener { authResultTask ->
            if (authResultTask.isSuccessful) {
                callback(true, "RegistrationSuccess", authResultTask.result?.user?.uid ?: "")
            } else {
                callback(false, authResultTask.exception?.message, "")
            }
        }) ?: callback(false, "createUserWithEmailAndPassword returned null", "")
    }
}
