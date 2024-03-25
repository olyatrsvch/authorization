package com.example.googleit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.googleit.MainActivity
import com.example.googleit.R
import com.example.googleit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = FragmentLoginBinding.inflate(inflater)
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(loginBinding.root)

        activity?.findViewById<View>(R.id.bottomNavigationView)?.visibility = View.GONE

        val activityMain = activity as? MainActivity

        val currentUser = activityMain?.auth?.currentUser
        if (currentUser != null) {
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }

        // Setting onClickListeners
        with (loginBinding) {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                activityMain?.auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success
                            navController.navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            // If sign in fails
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            etEmail.text.clear()
                            etPassword.text.clear()
                        }
                    }
            }

            btnSignUp.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }
}