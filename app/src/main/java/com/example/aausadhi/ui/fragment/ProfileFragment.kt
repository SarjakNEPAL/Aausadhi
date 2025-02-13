package com.example.pharmabucket.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pharmabucket.R
import com.example.pharmabucket.databinding.FragmentProfileBinding
import com.example.pharmabucket.repository.UserRepositoryImpl
import com.example.pharmabucket.ui.UserModel
import com.example.pharmabucket.viewmodel.UserViewModel


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var repo=UserRepositoryImpl()
        userViewModel=UserViewModel(repo)

        var currentUser= userViewModel.getCurrentUser() //gets useraUTH DATA
        currentUser.let{
            userViewModel.getDataFromDB(it?.uid.toString()){
                user,success,message->
                binding.profileName.text= user?.fullName
            }
        }
        //context this X , requireContext() OK

        userViewModel.userData.observe(requireActivity()){
            users->
            binding.profileEmail.text= currentUser?.email
            binding.profileName.text= users?.fullName
        }
    }
}