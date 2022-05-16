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
import com.example.hydrate.R.id.createAccountButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationFragment : BaseFragment() {

    private val REG_DEBUG = "REG_DEBUG"
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSingUpClick(view)
    }

    private fun setupSingUpClick(view: View) {
            view.findViewById<Button>(createAccountButton).setOnClickListener {
                val email = (view.findViewById<EditText>(R.id.emailLoginRegistration).text).toString().trim()
                val password = (view.findViewById<EditText>(R.id.passwordLoginRegistration).text).toString().trim()
                val passwordRepeat = (view.findViewById<EditText>(R.id.passwordRepeatLogin).text).toString().trim()
                val database = Firebase.database.reference
                if (email.isNotEmpty() && password.isNotEmpty() && passwordRepeat.isNotEmpty()) {
                    if(password == passwordRepeat) {
                        fbAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener { authRes ->
                                if(authRes.user != null) {
                                    findNavController().navigate(R.id.action_registrationFragment_to_profileCreationFragment)
                                    Toast.makeText(
                                        activity,
                                        "Account has been created, create your profile",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                    else {
                        Toast.makeText(
                            activity,
                            "Passwords doesn't match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}