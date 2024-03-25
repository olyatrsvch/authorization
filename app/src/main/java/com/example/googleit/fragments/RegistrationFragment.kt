package com.example.googleit.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.googleit.MainActivity
import com.example.googleit.R
import com.example.googleit.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {

    private lateinit var registrationBinding: FragmentRegistrationBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registrationBinding = FragmentRegistrationBinding.inflate(inflater)
        return registrationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(registrationBinding.root)
        val activityMain = activity as? MainActivity

        with (registrationBinding) {
            btnSignUpRegistration.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                activityMain?.auth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            val user = Firebase.auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = etName.text.toString()
                            }
                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User profile updated.")
                                    }
                                }

                            navController.navigate(R.id.action_registrationFragment_to_homeFragment)
                        } else {
                            // If sign in fails
                            Toast.makeText(
                                context, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}