package com.example.hydrate.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hydrate.R
import com.example.hydrate.database.Users
import com.example.hydrate.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class ProfileFragment : Fragment() {

    var name : String = "empty"
    var goal : Double = 0.0
    var weight : Double = 0.0
    var height : Double = 0.0
    var gender : String = "empty"
    var progress : Double = 0.0

    private var _binding: FragmentProfileBinding? = null
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var myRef: DatabaseReference
    val firebase = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
    val user_id = fbAuth.uid.toString()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.example.hydrate.R.layout.fragment_profile, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onDisplayProfile(view)
        onSaveNewProfile(view)
        onRefreshProfile(view)

        view.findViewById<Button>(com.example.hydrate.R.id.logoutButton).setOnClickListener {
            fbAuth.signOut()
            view.findNavController().navigate(com.example.hydrate.R.id.action_navigation_profile_to_registrationActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.Settings) {
            findNavController().navigate(R.id.action_navigation_profile_to_settingsFragment2)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onSaveNewProfile(view: View) {
        view.findViewById<ImageButton>(R.id.save_name).setOnClickListener {
            name = view.findViewById<EditText>(R.id.changeName).text.toString()
            val user = Users(user_id, name, goal, weight, height, gender)
            myRef.child("Users").child(user_id).child("UserData").setValue(user)

        }

        view.findViewById<ImageButton>(R.id.save_goal).setOnClickListener {
            val prev_goal = goal
            if (view.findViewById<EditText>(R.id.changeGoal).text.isEmpty()) {
                goal = prev_goal
            }
            else {
                goal = view.findViewById<EditText>(R.id.changeGoal).text.toString().toDouble()
            }

            val user = Users(user_id, name, goal, weight, height, gender)
            myRef.child("Users").child(user_id).child("UserData").setValue(user)
        }

        view.findViewById<ImageButton>(R.id.save_weight).setOnClickListener {
            val prev_weight = weight
            if (view.findViewById<EditText>(R.id.changeWeight).text.isEmpty()) {
                weight = prev_weight
            }
            else {
                weight = view.findViewById<EditText>(R.id.changeWeight).text.toString().toDouble()
            }

            val user = Users(user_id, name, goal, weight, height, gender)
            myRef.child("Users").child(user_id).child("UserData").setValue(user)
        }

        view.findViewById<ImageButton>(R.id.save_height).setOnClickListener {
            val prev_height = height
            if (view.findViewById<EditText>(R.id.changeHeight).text.isEmpty()) {
                height = prev_height
            }
            else {
                height = view.findViewById<EditText>(R.id.changeHeight).text.toString().toDouble()
            }

            val user = Users(user_id, name, goal, weight, height, gender)
            myRef.child("Users").child(user_id).child("UserData").setValue(user)
        }

        view.findViewById<ImageButton>(R.id.save_gender).setOnClickListener {
            val radioGroup = view.findViewById<RadioGroup>(R.id.radioChange)
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (R.id.toMaleButton == checkedId) gender = "male"
                if  (R.id.toFemaleButton == checkedId) gender = "female"
            }

            val user = Users(user_id, name, goal, weight, height, gender)
            myRef.child("Users").child(user_id).child("UserData").setValue(user)
        }

    }

    private fun onRefreshProfile(view: View) {
        view.findViewById<ImageButton>(R.id.refresh_button).setOnClickListener {
            onDisplayProfile(view)
        }
    }

    private fun onDisplayProfile(view: View) {

        myRef = firebase.reference
        val user_id = fbAuth.uid.toString()

        myRef.child("Users").child(user_id).child("UserData").child("name").get().addOnSuccessListener {
            name = it.value.toString()
            val textView = view.findViewById(com.example.hydrate.R.id.user_name) as TextView
            textView.text = name

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        myRef.child("Users").child(user_id).child("UserData").child("goal").get().addOnSuccessListener {
            goal = it.value.toString().toDouble()
            val textView = view.findViewById(com.example.hydrate.R.id.user_goal) as TextView
            textView.text = goal.toString()

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        myRef.child("Users").child(user_id).child("UserData").child("weight").get().addOnSuccessListener {
            weight = it.value.toString().toDouble()
            val textView = view.findViewById(com.example.hydrate.R.id.user_weight) as TextView
            textView.text = weight.toString()

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        myRef.child("Users").child(user_id).child("UserData").child("height").get().addOnSuccessListener {
            height = it.value.toString().toDouble()
            val textView = view.findViewById(com.example.hydrate.R.id.user_height) as TextView
            textView.text = height.toString()

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        myRef.child("Users").child(user_id).child("UserData").child("gender").get().addOnSuccessListener {
            gender = it.value.toString()
            val textView = view.findViewById(com.example.hydrate.R.id.user_gender) as TextView
            textView.text = gender

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}