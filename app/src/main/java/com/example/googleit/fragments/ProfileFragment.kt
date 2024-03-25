package com.example.googleit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.googleit.R
import com.example.googleit.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater)
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<View>(R.id.bottomNavigationView)?.visibility = View.VISIBLE

        navController = Navigation.findNavController(profileBinding.root)

        val user = Firebase.auth.currentUser
        val name = user?.displayName
        val email = user?.email

        with(profileBinding) {
            tvName.text = getString(R.string.tvName, name)
            tvEmail.text = getString(R.string.tvEmail, email)

            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                navController.navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }
}