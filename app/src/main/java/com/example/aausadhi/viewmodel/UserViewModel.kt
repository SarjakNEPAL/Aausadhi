package com.example.aausadhi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.aausadhi.model.UserModel
import com.example.aausadhi.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo:UserRepository) {
    fun login(email:String,password:String,callback:(Boolean,String)->Unit){
        repo.login(email,password,callback)
    }
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit){
        repo.signup(email,password,callback)
    }
    fun forgetPassword(email:String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun addUserToDatabase(userID:String, userModel: UserModel, callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userID, userModel,callback)
    }
    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }
    var _userData=MutableLiveData<UserModel?>()
    var userData=MutableLiveData<UserModel?>() //getter
        get() = _userData

    fun getDataFromDB(userID: String){
        repo.getDataFromDB(userID){
                userModel,sucess,message->
            if(sucess){
                _userData.value = userModel
            }else{
                _userData.value = null
            }
        }
    }
    fun logout(callback: (Boolean, String) -> Unit){
        return repo.logout(callback)
    }
    fun editProfile(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit){
        repo.editProfile(userID,data,callback)
    }
}