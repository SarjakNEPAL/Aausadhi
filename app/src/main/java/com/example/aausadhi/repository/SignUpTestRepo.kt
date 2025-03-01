package com.example.aausadhi.repository

interface SignUpTestRepo {
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit)

}