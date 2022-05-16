package com.example.hydrate.user_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.hydrate.R
import com.example.hydrate.database.UserCalendarStreak
import com.example.hydrate.database.UserCounter
import com.example.hydrate.database.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.Calendar.DAY_OF_WEEK

class ProfileCreationFragment : BaseFragment() {

    var gender = "pick"

    private lateinit var myRef: DatabaseReference
    private val fbAuth = FirebaseAuth.getInstance()
    val firebase = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
    val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_creation, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.maleButton == checkedId) gender = "male"
            if  (R.id.femaleButton == checkedId) gender = "female"
        }

        setupCreationProfile(view)
    }

    private fun setupCreationProfile(view: View) {
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            if (view.findViewById<EditText>(R.id.nameInput).text.isNotEmpty()
                && view.findViewById<EditText>(R.id.goalInput).text.isNotEmpty()
                && view.findViewById<EditText>(R.id.weightInput).text.isNotEmpty()
                && view.findViewById<EditText>(R.id.heightInput).text.isNotEmpty()) {
            val name = view.findViewById<EditText>(R.id.nameInput).text.toString()
            val goal = view.findViewById<EditText>(R.id.goalInput).text.toString().toDouble()
            val weight = view.findViewById<EditText>(R.id.weightInput).text.toString().toDouble()
            val height = view.findViewById<EditText>(R.id.heightInput).text.toString().toDouble()
            val user_id = fbAuth.uid.toString()

                val user = Users(user_id, name, goal, weight, height, gender)

                var caloriesCounter = 0.0
                var sugarCounter = 0.0
                var alcoholCounter = 0.0
                var caffeineCounter = 0.0
                var potassiumCounter = 0.0
                var sodiumCounter = 0.0
                var proteinCounter = 0.0
                var calciumCounter = 0.0
                var vitaminsCounter = 0.0
                var magnesiumCounter = 0.0
                var progressCounter = 0.0
                var currentDay = calendar.get(DAY_OF_WEEK)
                val user_counter = UserCounter(caloriesCounter, sugarCounter, alcoholCounter, caffeineCounter, potassiumCounter, sodiumCounter,
                        proteinCounter, calciumCounter, vitaminsCounter, magnesiumCounter, progressCounter, user_id, currentDay)
                myRef = firebase.reference
                myRef.child("Users").child(user_id).child("UserData").setValue(user)
                myRef.child("Users").child(user_id).child("UserCounter").setValue(user_counter)

                val userCalendarStreak = UserCalendarStreak(user_id, 0.0, progressCounter)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Sunday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Monday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Tuesday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Wednesday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Thursday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Friday").setValue(userCalendarStreak)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Saturday").setValue(userCalendarStreak)

                startApp()
            }
            else {
                Toast.makeText(
                    activity,
                    "Fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}