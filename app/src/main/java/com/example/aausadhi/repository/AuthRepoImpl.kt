package com.example.aausadhi.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(var auth:FirebaseAuth): AuthRepo{
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ //calling login func
            if (it.isSuccessful){
                callback(true,"Login Success")
            }else{
                callback(false,it.exception?.message.toString()) //passes error message
            }
        }
    }
}