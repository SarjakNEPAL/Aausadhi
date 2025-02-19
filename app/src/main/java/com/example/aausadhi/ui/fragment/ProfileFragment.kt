package com.example.aausadhi.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aausadhi.databinding.FragmentProfileBinding
import com.example.aausadhi.repository.UserRepositoryImpl
import com.example.aausadhi.ui.activity.LoginActivity
import com.example.aausadhi.ui.activity.ProductDashboardActivity
import com.example.aausadhi.ui.activity.UpdateProfileActivity
import com.example.aausadhi.viewmodel.UserViewModel
import com.example.aausadhi.utils.LocalStorage

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Get the current user
        val currentUser = userViewModel.getCurrentUser()
        currentUser?.let {
            userViewModel.getDataFromDB(currentUser.uid)
        }

        // Observe user data
        userViewModel.userData.observe(viewLifecycleOwner) { users ->
            binding.emailData.text = currentUser?.email
            binding.nameData.text = users?.fullName
            binding.locationData.text = users?.address
            binding.phoneData.text = users?.phoneNumber
        }

        // Edit profile click listener
        binding.editProfile.setOnClickListener {
            val intent = Intent(context, UpdateProfileActivity::class.java)
            context?.startActivity(intent)
        }

        // Logout click listener
        binding.logoutSection.setOnClickListener {
            userViewModel.logout { success, message ->
                if (success) {
                    // Clear user credentials
                    LocalStorage.clearUserCredentials()
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
