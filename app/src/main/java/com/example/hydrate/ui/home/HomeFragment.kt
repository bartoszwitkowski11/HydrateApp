package com.example.hydrate.ui.home

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.example.hydrate.R
import com.example.hydrate.R.*
import com.example.hydrate.database.UserCalendarStreak
import com.example.hydrate.database.UserCounter
import com.example.hydrate.database.UserDrinksHistory
import com.example.hydrate.databinding.FragmentHomeBinding
import com.example.hydrate.ui.drink.ItemFragmentDrinkArgs
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.leinardi.android.speeddial.SpeedDialView
import java.text.DecimalFormat
import java.util.*
import java.util.Calendar.DAY_OF_WEEK

class HomeFragment : Fragment() {

    var cup_size : Double = 1.0
    private var _binding: FragmentHomeBinding? = null
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var myRef: DatabaseReference
    val firebase = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
    val user_id = fbAuth.uid.toString()
    val calendar = Calendar.getInstance()
    lateinit var recyclerView : RecyclerView

    val args: ItemFragmentDrinkArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            onSaveInstanceState(savedInstanceState)
        }

        onDisplayStats(view)
        onDisplayWeeklyStreak(view)
        BMIcalculator(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.Settings) {
            findNavController().navigate(R.id.action_navigation_home_to_settingsFragment)
        }

        return super.onOptionsItemSelected(item)
    }


    private fun onDisplayWeeklyStreak(view: View) {
        myRef = firebase.reference
        myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Monday").child("goal").get().addOnSuccessListener {
            val goal = it.value.toString().toDouble()
            myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Monday").child("progress").get().addOnSuccessListener {
                val progress = it.value.toString().toDouble()
                val dayView = view.findViewById<ImageView>(R.id.MondayView)
                val percentageView = view.findViewById<TextView>(R.id.mon_percentage)
                calendarStreak(view, goal, progress, dayView, percentageView)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Tuesday").child("goal").get().addOnSuccessListener {
                    val goal = it.value.toString().toDouble()
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Tuesday").child("progress").get().addOnSuccessListener {
                    val progress = it.value.toString().toDouble()
                    val dayView = view.findViewById<ImageView>(R.id.TuesdayView)
                    val percentageView = view.findViewById<TextView>(R.id.tue_percentage)
                    calendarStreak(view, goal, progress, dayView, percentageView)
                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Wednesday").child("goal").get().addOnSuccessListener {
                    val goal = it.value.toString().toDouble()
                    myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Wednesday").child("progress").get().addOnSuccessListener {
                        val progress = it.value.toString().toDouble()
                        val dayView = view.findViewById<ImageView>(R.id.WednesdayView)
                        val percentageView = view.findViewById<TextView>(R.id.wed_percentage)
                        calendarStreak(view, goal, progress, dayView, percentageView)
                        myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Thursday").child("goal").get().addOnSuccessListener {
                            val goal = it.value.toString().toDouble()
                            myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Thursday").child("progress").get().addOnSuccessListener {
                                val progress = it.value.toString().toDouble()
                                val dayView = view.findViewById<ImageView>(R.id.ThursdayView)
                                val percentageView = view.findViewById<TextView>(R.id.thu_percentage)
                                calendarStreak(view, goal, progress, dayView, percentageView)
                                    myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Friday").child("goal").get().addOnSuccessListener {
                                        val goal = it.value.toString().toDouble()
                                        myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Friday").child("progress").get().addOnSuccessListener {
                                            val progress = it.value.toString().toDouble()
                                            val dayView = view.findViewById<ImageView>(R.id.FridayView)
                                            val percentageView = view.findViewById<TextView>(R.id.fri_percentage)
                                            calendarStreak(view, goal, progress, dayView, percentageView)
                                            myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Saturday").child("goal").get().addOnSuccessListener {
                                                val goal = it.value.toString().toDouble()
                                                myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Saturday").child("progress").get().addOnSuccessListener {
                                                    val progress = it.value.toString().toDouble()
                                                    val dayView = view.findViewById<ImageView>(R.id.SaturdayView)
                                                    val percentageView = view.findViewById<TextView>(R.id.sat_percentage)
                                                    calendarStreak(view, goal, progress, dayView, percentageView)
                                                    myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Sunday").child("goal").get().addOnSuccessListener {
                                                        val goal = it.value.toString().toDouble()
                                                        myRef.child("Users").child(user_id).child("UserCalendarStreak").child("Sunday").child("progress").get().addOnSuccessListener {
                                                            val progress = it.value.toString().toDouble()
                                                            val dayView = view.findViewById<ImageView>(R.id.SundayView)
                                                            val percentageView = view.findViewById<TextView>(R.id.sun_percentage)
                                                            calendarStreak(view, goal, progress, dayView, percentageView)
                                                            }

                                                    }

                                                }

                                            }

                                        }

                                    }
                                    }

                            }

                        }

                    }

                }

                }

            }

        }
    }

    private fun onDisplayStats(view: View) {
                myRef = firebase.reference
                val today = calendar.get(DAY_OF_WEEK)
                myRef.child("Users").child(user_id).child("UserCounter").child("currentDay").get().addOnSuccessListener {
                    val lastProgressDay = it.value.toString().toInt()
                    if (today == lastProgressDay) {
                        download_counter(view)
                    }
                    else {
                        val day = WhatDayIsToday(lastProgressDay)
                        myRef.child("Users").child(user_id).child("UserCounter").child("progressCounter").get().addOnSuccessListener {
                            val progress = it.value.toString().toDouble()
                            myRef.child("Users").child(user_id).child("UserData").child("goal").get().addOnSuccessListener {
                                val goal = it.value.toString().toDouble()

                                val userCalendarStreak = UserCalendarStreak(user_id, goal, progress)
                                myRef.child("Users").child(user_id).child("UserCalendarStreak").child(day).setValue(userCalendarStreak)
                            }
                        }
                        var progress = 0.0
                        var caloriesCounter = 0.0
                        var sugarCounter = 0.0
                        var alcoholCounter = 0.0
                        var coffeineCounter = 0.0
                        var potassiumCounter = 0.0
                        var sodiumCounter = 0.0
                        var proteinCounter = 0.0
                        var calciumCounter = 0.0
                        var vitaminsCounter = 0.0
                        var magnesiumCounter = 0.0
                        var currentDay = calendar.get(DAY_OF_WEEK)
                        val user_counter = UserCounter(caloriesCounter, sugarCounter, alcoholCounter, coffeineCounter, potassiumCounter, sodiumCounter,
                            proteinCounter, calciumCounter, vitaminsCounter, magnesiumCounter, progress, user_id, currentDay)
                        myRef.child("Users").child(user_id).child("UserCounter").setValue(user_counter)
                        myRef.child("Users").child(user_id).child("UserDrinksHistory").setValue(null)

                        onDisplayStats(view)
                    }
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun WhatDayIsToday(day: Int): String {
        var today = "empty"
        if (day == 1) today = "Sunday"
        else if (day == 2) today = "Monday"
        else if (day == 3) today= "Tuesday"
        else if (day == 4) today = "Wednesday"
        else if (day == 5) today = "Thursday"
        else if (day == 6) today = "Friday"
        else if (day == 7) today = "Saturday"

        return today
    }

    fun download_counter(view: View) {
        val donutView = view.findViewById<DonutProgressView>(R.id.donut_view)
        val speedDialView = view.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(menu.fab_menu)

        val sharedPref = activity?.getSharedPreferences("com.example.hydrate_preferences",Context.MODE_PRIVATE) ?: return
        myRef.child("Users").child(user_id).child("UserData").child("goal").get()
            .addOnSuccessListener {
                val goal = it.value.toString().toDouble()
                val textView = view.findViewById(R.id.goalText) as TextView
                textView.text = (DecimalFormat("##.##").format(goal)).toString() + " ml"
                donutView.cap = goal.toFloat()
                myRef.child("Users").child(user_id).child("UserCounter").child("progressCounter").get().addOnSuccessListener {
                    val progress = it.value.toString().toDouble()
                    val textView = view.findViewById(R.id.progressText) as TextView
                    textView.text = (DecimalFormat("##.##").format(progress)).toString() + " ml"

                    val section1 = DonutSection(
                        name = "section_1",
                        color = Color.parseColor("#2BBBFD"),
                        amount = progress.toFloat()
                    )

                    donutView.submitData(listOf(section1))

                    val result_percentage = 100 * (progress / goal)
                    val textView2 = view.findViewById(R.id.percentageText) as TextView
                    textView2.text = (DecimalFormat("##").format(result_percentage)).toString() + "%"

                    myRef.child("Users").child(user_id).child("UserCounter").child("sugarCounter").get().addOnSuccessListener {
                        val sugarCounter = it.value.toString().toDouble()
                        val textView = view.findViewById(R.id.sugarCounter) as TextView
                        onDisplayScale(sugarCounter, textView)

                        myRef.child("Users").child(user_id).child("UserCounter").child("caloriesCounter").get().addOnSuccessListener {
                            val caloriesCounter = it.value.toString().toDouble()
                            val textView = view.findViewById(R.id.caloriesCounter) as TextView
                            textView.text = (DecimalFormat("##.##").format(caloriesCounter)).toString() + " kcal"
                            myRef.child("Users").child(user_id).child("UserCounter").child("alcoholCounter").get().addOnSuccessListener {
                                val alcoholCounter = it.value.toString().toDouble()
                                val textView = view.findViewById(R.id.alcoholCounter) as TextView
                                textView.text = (DecimalFormat("##.##").format(alcoholCounter)).toString() + " ml"
                                myRef.child("Users").child(user_id).child("UserCounter").child("caffeineCounter").get().addOnSuccessListener {
                                    val caffeineCounter = it.value.toString().toDouble()
                                    val textView = view.findViewById(R.id.caffeineCounter) as TextView
                                    onDisplayScale(caffeineCounter, textView)
                                    myRef.child("Users").child(user_id).child("UserCounter").child("magnesiumCounter").get().addOnSuccessListener {
                                        val magnesiumCounter = it.value.toString().toDouble()
                                        val textView = view.findViewById(R.id.magnesiumCounter) as TextView
                                        onDisplayScale(magnesiumCounter, textView)
                                        myRef.child("Users").child(user_id).child("UserCounter").child("potassiumCounter").get().addOnSuccessListener {
                                            val potassiumCounter = it.value.toString().toDouble()
                                            val textView = view.findViewById(R.id.potassiumCounter) as TextView
                                            onDisplayScale(potassiumCounter, textView)
                                            myRef.child("Users").child(user_id).child("UserCounter").child("sodiumCounter").get().addOnSuccessListener {
                                                val sodiumCounter = it.value.toString().toDouble()
                                                val textView = view.findViewById(R.id.sodiumCounter) as TextView
                                                onDisplayScale(sodiumCounter, textView)
                                                myRef.child("Users").child(user_id).child("UserCounter").child("proteinCounter").get().addOnSuccessListener {
                                                    val proteinCounter = it.value.toString().toDouble()
                                                    val textView = view.findViewById(R.id.proteinCounter) as TextView
                                                    onDisplayScale(proteinCounter, textView)
                                                    myRef.child("Users").child(user_id).child("UserCounter").child("calciumCounter").get().addOnSuccessListener {
                                                        val calciumCounter = it.value.toString().toDouble()
                                                        val textView = view.findViewById(R.id.calciumCounter) as TextView
                                                        onDisplayScale(calciumCounter, textView)
                                                        myRef.child("Users").child(user_id).child("UserCounter").child("vitaminsCounter").get().addOnSuccessListener {
                                                            val vitaminsCounter = it.value.toString().toDouble()
                                                            val textView = view.findViewById(R.id.vitaminsCounter) as TextView
                                                            onDisplayScale(vitaminsCounter, textView)
                                                            myRef.child("Users").child(user_id).child("UserCounter").child("currentDay").get().addOnSuccessListener {
                                                                val currentDay = it.value.toString().toInt()
                                                                onDisplayDrinksHistory(view, progress, caloriesCounter, sugarCounter, alcoholCounter, caffeineCounter, magnesiumCounter, potassiumCounter, sodiumCounter, proteinCounter, calciumCounter, vitaminsCounter, currentDay)

                                                                speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
                                                                    when (actionItem.id) {
                                                                        R.id.smallDrink -> {
                                                                            speedDialView.close() // To close the Speed Dial with animation
                                                                            val time = Date().time.toInt()

                                                                            cup_size = (sharedPref.getString("cup_one", "200")).toString().toDouble()
                                                                            val action = HomeFragmentDirections.actionNavigationHomeToItemFragmentDrink2("water", cup_size.toFloat(),
                                                                                sugarCounter.toFloat(), caloriesCounter.toFloat(), alcoholCounter.toFloat(), caffeineCounter.toFloat(),
                                                                                magnesiumCounter.toFloat(), potassiumCounter.toFloat(), sodiumCounter.toFloat(), proteinCounter.toFloat(),
                                                                                calciumCounter.toFloat(), vitaminsCounter.toFloat(), progress.toFloat(), currentDay, time)
                                                                            findNavController(view).navigate(action)

                                                                            return@OnActionSelectedListener true // false will close it without animation
                                                                        }

                                                                        R.id.mediumDrink -> {
                                                                            speedDialView.close() // To close the Speed Dial with animation
                                                                            val time = Date().time.toInt()

                                                                            cup_size = (sharedPref.getString("cup_two", "350")).toString().toDouble()
                                                                            val action = HomeFragmentDirections.actionNavigationHomeToItemFragmentDrink2("water", cup_size.toFloat(),
                                                                                sugarCounter.toFloat(), caloriesCounter.toFloat(), alcoholCounter.toFloat(), caffeineCounter.toFloat(),
                                                                                magnesiumCounter.toFloat(), potassiumCounter.toFloat(), sodiumCounter.toFloat(), proteinCounter.toFloat(),
                                                                                calciumCounter.toFloat(), vitaminsCounter.toFloat(), progress.toFloat(), currentDay, time)
                                                                            findNavController(view).navigate(action)

                                                                            return@OnActionSelectedListener true // false will close it without animation
                                                                        }

                                                                        R.id.bigDrink -> {
                                                                            speedDialView.close() // To close the Speed Dial with animation
                                                                            val time = Date().time.toInt()

                                                                            cup_size = (sharedPref.getString("cup_three", "500")).toString().toDouble()
                                                                            val action = HomeFragmentDirections.actionNavigationHomeToItemFragmentDrink2("water", cup_size.toFloat(),
                                                                                sugarCounter.toFloat(), caloriesCounter.toFloat(), alcoholCounter.toFloat(), caffeineCounter.toFloat(),
                                                                                magnesiumCounter.toFloat(), potassiumCounter.toFloat(), sodiumCounter.toFloat(), proteinCounter.toFloat(),
                                                                                calciumCounter.toFloat(), vitaminsCounter.toFloat(), progress.toFloat(), currentDay, time)
                                                                            findNavController(view).navigate(action)

                                                                            return@OnActionSelectedListener true // false will close it without animation
                                                                        }

                                                                        R.id.Other -> {
                                                                            speedDialView.close() // To close the Speed Dial with animation
                                                                            val time = Date().time.toInt()
                                                                            val builder = AlertDialog.Builder(context)
                                                                            val viewInflated: View = LayoutInflater.from(context).inflate(layout.dialog_layout, getView() as ViewGroup?, false)

                                                                            builder.setTitle("Add custom sized drink")
                                                                            builder.setView(viewInflated)
                                                                            builder.setPositiveButton("Add") { dialog, which ->
                                                                               dialog.dismiss()
                                                                               cup_size = viewInflated.findViewById<EditText>(R.id.drinkSizeInput).text.toString().toDouble()
                                                                               val action = HomeFragmentDirections.actionNavigationHomeToItemFragmentDrink2("water", cup_size.toFloat(),
                                                                                       sugarCounter.toFloat(), caloriesCounter.toFloat(), alcoholCounter.toFloat(), caffeineCounter.toFloat(),
                                                                                       magnesiumCounter.toFloat(), potassiumCounter.toFloat(), sodiumCounter.toFloat(), proteinCounter.toFloat(),
                                                                                       calciumCounter.toFloat(), vitaminsCounter.toFloat(), progress.toFloat(), currentDay, time)
                                                                               findNavController(view).navigate(action)
                                                                            }
                                                                            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

                                                                            builder.show()

                                                                            return@OnActionSelectedListener true // false will close it without animation
                                                                        }
                                                                    }
                                                                    false
                                                                })
                                                            }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                }
            }

    }

    fun BMIcalculator(view: View) {
        val textView = view.findViewById(R.id.BMIstats) as TextView
        val BMIView = view.findViewById(R.id.bmiDescription) as TextView
        myRef.child("Users").child(user_id).child("UserData").child("height").get()
            .addOnSuccessListener {
                val height = (it.value.toString().toDouble()) / 100
                myRef.child("Users").child(user_id).child("UserData").child("weight").get()
                    .addOnSuccessListener {
                        val weight = it.value.toString().toDouble()
                        var BMI = weight / (height * height)
                        if (BMI < 16.0) {
                            BMIView.setTextColor(Color.parseColor("#FF0000"))
                            BMIView.text = "Severe thinness"
                            textView.setTextColor(Color.parseColor("#FF0000"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 16.0 && BMI < 17.0) {
                            BMIView.setTextColor(Color.parseColor("#DC4C4C"))
                            BMIView.text = "Moderate Thinness"
                            textView.setTextColor(Color.parseColor("#DC4C4C"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 17.0 && BMI < 18.5) {
                            BMIView.setTextColor(Color.parseColor("#FFCE00"))
                            BMIView.text = "Mild Thinness"
                            textView.setTextColor(Color.parseColor("#FFCE00"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 18.5 && BMI < 25.0) {
                            BMIView.setTextColor(Color.parseColor("#00B238"))
                            BMIView.text = "Normal"
                            textView.setTextColor(Color.parseColor("#00B238"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 25.0 && BMI < 30.0) {
                            BMIView.setTextColor(Color.parseColor("#FFCE00"))
                            BMIView.text = "Overweight"
                            textView.setTextColor(Color.parseColor("#FFCE00"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 30.0 && BMI < 35.0) {
                            BMIView.setTextColor(Color.parseColor("#DC4C4C"))
                            BMIView.text = "Obese class I"
                            textView.setTextColor(Color.parseColor("#DC4C4C"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 35.0 && BMI < 40.0) {
                            BMIView.setTextColor(Color.parseColor("#FF0000"))
                            BMIView.text = "Obese class II"
                            textView.setTextColor(Color.parseColor("#FF0000"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()
                        }
                        if (BMI >= 40.0) {
                            BMIView.setTextColor(Color.parseColor("#990000"))
                            BMIView.text = "Obese class III"
                            textView.setTextColor(Color.parseColor("#990000"))
                            textView.text = (DecimalFormat("##.##").format(BMI)).toString()

                        }

                    }
            }
    }

    fun onDisplayScale(Counter: Double, textView: TextView) {
        if (Counter >= 100) {
            textView.text = (DecimalFormat("##.##").format(Counter/1000)).toString() + " g"
        }
        else {
            textView.text = (DecimalFormat("##.##").format(Counter)).toString() + " mg"
        }
    }

    fun calendarStreak(view: View, goal: Double, progress: Double, dayView: ImageView, percentageView: TextView) {
        var result = (progress/goal) * 100

        if (goal > progress) {
            dayView.setImageResource(drawable.ic_not_completed)
            percentageView.setTextColor(resources.getColor(color.orange_700, null))
            percentageView.text = (DecimalFormat("##.#").format(result)).toString() + "%"
        }
        if (progress >= goal) {
            dayView.setImageResource(drawable.ic_completed)
            percentageView.setTextColor(resources.getColor(color.dark_blue_500, null))
            percentageView.text = (DecimalFormat("##.#").format(result)).toString() + "%"
        }
        if (progress == 0.0) {
            dayView.setImageResource(drawable.ic_empty)
            percentageView.setTextColor(Color.parseColor("#676767"))
            percentageView.text = "0%"
        }
        if (goal == 0.0) {
            dayView.setImageResource(drawable.ic_empty)
            percentageView.setTextColor(Color.parseColor("#676767"))
            percentageView.text = "0%"
        }
    }

    fun onDisplayDrinksHistory(view: View, progressCounter: Double, caloriesCounter: Double, sugarCounter: Double, alcoholCounter: Double, caffeineCounter: Double, magnesiumCounter: Double,
                               potassiumCounter: Double, sodiumCounter: Double, proteinCounter: Double, calciumCounter: Double, vitaminsCounter: Double, currentDay: Int) {
        recyclerView = view.findViewById(R.id.HistoryList)

        var query = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
            .child("Users")
            .child(user_id)
            .child("UserDrinksHistory")
            .orderByValue()
            .limitToLast(90)

        var options: FirebaseRecyclerOptions<UserDrinksHistory> = FirebaseRecyclerOptions.Builder<UserDrinksHistory>()
            .setQuery(query, object : SnapshotParser<UserDrinksHistory> {
                override fun parseSnapshot(snapshot: DataSnapshot): UserDrinksHistory {

                    var drinkName = snapshot.child("drinkName").getValue(String::class.java)
                    var cupSize = snapshot.child("cupSize").getValue(Double::class.java)
                    var time = snapshot.child("time").getValue(Int::class.java)

                    return UserDrinksHistory(drinkName, cupSize, time)
                }
            })
            .build()

        var FirebaseAdapter =
            object : FirebaseRecyclerAdapter<UserDrinksHistory, UserDrinksHistoryHolder>(options) {
                override fun onBindViewHolder(
                    holder: UserDrinksHistoryHolder,
                    position: Int,
                    model: UserDrinksHistory,
                ) {
                    holder.drinkName.text = model.drinkName.toString()
                    holder.cupSize.text = (DecimalFormat("##.##").format(model.cupSize)).toString() + " ml"

                    holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                        Toast.makeText(
                                activity,
                                "You deleted " + (DecimalFormat("##.##").format(model.cupSize)).toString() + " ml "  + model.drinkName.toString(),
                                Toast.LENGTH_SHORT
                        ).show()
                        myRef.child("Beverages").child(model.drinkName.toString()).child("Calories").get().addOnSuccessListener {
                            val calories = it.value.toString().toDouble()
                            myRef.child("Beverages").child(model.drinkName.toString()).child("Sugar").get().addOnSuccessListener {
                                val sugar = it.value.toString().toDouble()
                                myRef.child("Beverages").child(model.drinkName.toString()).child("Alcohol").get().addOnSuccessListener {
                                    val alcohol = it.value.toString().toDouble()
                                    myRef.child("Beverages").child(model.drinkName.toString()).child("Caffeine").get().addOnSuccessListener {
                                        val caffeine = it.value.toString().toDouble()
                                        myRef.child("Beverages").child(model.drinkName.toString()).child("Magnesium").get().addOnSuccessListener {
                                            val magnesium = it.value.toString().toDouble()
                                            myRef.child("Beverages").child(model.drinkName.toString()).child("Potassium").get().addOnSuccessListener {
                                                val potassium = it.value.toString().toDouble()
                                                myRef.child("Beverages").child(model.drinkName.toString()).child("Sodium").get().addOnSuccessListener {
                                                    val sodium = it.value.toString().toDouble()
                                                    myRef.child("Beverages").child(model.drinkName.toString()).child("Protein").get().addOnSuccessListener {
                                                        val protein = it.value.toString().toDouble()
                                                        myRef.child("Beverages").child(model.drinkName.toString()).child("Calcium").get().addOnSuccessListener {
                                                            val calcium = it.value.toString().toDouble()
                                                            myRef.child("Beverages").child(model.drinkName.toString()).child("Vitamins").get().addOnSuccessListener {
                                                                val vitamins = it.value.toString().toDouble()

                                                                var progressMinus = progressCounter - model.cupSize.toString().toDouble()
                                                                var cup_ratio = model.cupSize.toString().toDouble()/100
                                                                var sugarCounterMinus =  sugarCounter - (sugar * cup_ratio)
                                                                var caloriesCounterMinus = caloriesCounter - (calories * cup_ratio)
                                                                var alcoholCounterMinus = alcoholCounter - (alcohol * cup_ratio)
                                                                var caffeineCounterMinus = caffeineCounter - (caffeine * cup_ratio)
                                                                var magnesiumCounterMinus = magnesiumCounter - (magnesium * cup_ratio)
                                                                var potassiumCounterMinus = potassiumCounter - (potassium * cup_ratio)
                                                                var sodiumCounterMinus = sodiumCounter - (sodium * cup_ratio)
                                                                var proteinCounterMinus = proteinCounter - (protein * cup_ratio)
                                                                var calciumCounterMinus = calciumCounter - (calcium * cup_ratio)
                                                                var vitaminsCounterMinus = vitaminsCounter - (vitamins * cup_ratio)

                                                                val user_counter = UserCounter(caloriesCounterMinus, sugarCounterMinus, alcoholCounterMinus, caffeineCounterMinus, potassiumCounterMinus, sodiumCounterMinus,
                                                                        proteinCounterMinus, calciumCounterMinus, vitaminsCounterMinus, magnesiumCounterMinus, progressMinus, user_id, currentDay)
                                                                myRef.child("Users").child(user_id).child("UserCounter").setValue(user_counter)
                                                                myRef.child("Users").child(user_id).child("UserDrinksHistory").child(model.time.toString()).setValue(null)
                                                                onDisplayStats(view)
                                                                onDisplayWeeklyStreak(view)

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        return@OnLongClickListener true
                    })

                    holder.itemView.setOnClickListener(View.OnClickListener {
                        Toast.makeText(
                                activity,
                                "If you want to delete " + (DecimalFormat("##.##").format(model.cupSize)).toString() + " ml " + model.drinkName.toString() + " hold your finger",
                                Toast.LENGTH_SHORT
                        ).show()
                    })

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDrinksHistoryHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(layout.fragment_home_drinks_history, parent, false)
                    return UserDrinksHistoryHolder(view)
                }

            }

        FirebaseAdapter.startListening()

        recyclerView.layoutManager = LinearLayoutManager(context)
        (recyclerView.layoutManager as LinearLayoutManager).setReverseLayout(true);
        (recyclerView.layoutManager as LinearLayoutManager).setStackFromEnd(true);
        recyclerView.adapter = FirebaseAdapter
    }

    class UserDrinksHistoryHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val container: View = mView.rootView
        val drinkName: TextView = mView.findViewById(id.drinkNameHistory)
        val cupSize: TextView = mView.findViewById(id.cupSizeHistory)
    }

}

