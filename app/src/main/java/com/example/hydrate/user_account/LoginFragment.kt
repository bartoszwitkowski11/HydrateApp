package com.example.hydrate.user_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hydrate.R
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : BaseFragment() {
    private val fbAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"
    val user_id = "uid"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginClick(view)
        setupRegistrationClick(view)


    }

    private fun setupRegistrationClick(view: View) {
        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun setupLoginClick(view: View) {
        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val email = (view.findViewById<EditText>(R.id.emailLogin).text).toString().trim()
            val password = (view.findViewById<EditText>(R.id.passwordLogin).text).toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                fbAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { authRes ->
                        if(authRes.user != null) {
                            startApp()
                        }

                    }
                    .addOnFailureListener { exc->
                        Toast.makeText(
                                activity,
                                exc.message.toString(),
                                Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

}