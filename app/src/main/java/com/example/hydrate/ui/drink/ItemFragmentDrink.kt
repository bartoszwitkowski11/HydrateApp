package com.example.hydrate.ui.drink

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hydrate.R
import com.example.hydrate.database.Beverages
import com.example.hydrate.database.UserCounter
import com.example.hydrate.database.UserDrinksHistory
import com.example.hydrate.ui.home.HomeFragmentArgs
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.Calendar.DAY_OF_WEEK

class ItemFragmentDrink : Fragment() {

    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var myRef: DatabaseReference
    val firebase = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
    val user_id = fbAuth.uid.toString()
    val calendar = Calendar.getInstance()

    lateinit var recyclerView : RecyclerView
    private var columnCount = 3
    var drink_picked = "water"

    val args: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                findNavController().navigate(R.id.action_itemFragmentDrink2_to_navigation_home7)
            R.id.Settings ->
                findNavController().navigate(R.id.action_itemFragmentDrink2_to_settingsFragment2)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list_drink, container, false)

        val textView = view.findViewById(R.id.cupSizeDisplay) as TextView
        textView.text = "You picked " + args.drinkSize.toString() + " ml cup size"

        recyclerView = view.findViewById(R.id.drinksAddList)


        var query = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
            .child("Beverages")
            .limitToLast(90)


        var options: FirebaseRecyclerOptions<Beverages> = FirebaseRecyclerOptions.Builder<Beverages>()
            .setQuery(query, object : SnapshotParser<Beverages> {
                override fun parseSnapshot(snapshot: DataSnapshot): Beverages {

                    var calories = snapshot.child("Calories").getValue(Double::class.java)
                    var sugar = snapshot.child("Sugar").getValue(Double::class.java)
                    var alcohol = snapshot.child("Alcohol").getValue(Double::class.java)
                    var caffeine = snapshot.child("Caffeine").getValue(Double::class.java)
                    var magnesium = snapshot.child("Magnesium").getValue(Double::class.java)
                    var potassium = snapshot.child("Potassium").getValue(Double::class.java)
                    var sodium = snapshot.child("Sodium").getValue(Double::class.java)
                    var protein = snapshot.child("Protein").getValue(Double::class.java)
                    var calcium = snapshot.child("Calcium").getValue(Double::class.java)
                    var vitamins = snapshot.child("Vitamins").getValue(Double::class.java)
                    var name = snapshot.child("Name").getValue(String::class.java)

                    return Beverages(calories, sugar, alcohol, caffeine, magnesium, potassium, sodium, protein, calcium, vitamins, name)
                }
            })
            .build()

        var FirebaseAdapter =
            object : FirebaseRecyclerAdapter<Beverages, BeveragesHolder>(options) {
                override fun onBindViewHolder(
                        holder: BeveragesHolder,
                        position: Int,
                        model: Beverages,
                ) {
                    holder.drinkAddName.text = model.Name.toString()

                    holder.itemView.setOnClickListener(View.OnClickListener {
                        drink_picked = model.Name.toString()
                        Toast.makeText(
                            activity,
                            "You picked " + args.drinkSize + "ml " + drink_picked,
                            Toast.LENGTH_SHORT
                        ).show()
                        myRef = firebase.reference

                            var progress = args.Progress + args.drinkSize.toDouble()
                            var cup_ratio = args.drinkSize.toDouble()/100
                            var sugarCounter = args.sugarProgress + (model.Sugar.toString().toDouble() * cup_ratio)
                            var caloriesCounter = args.caloriesProgress + (model.Calories.toString().toDouble() * cup_ratio)
                            var alcoholCounter = args.alcoholProgress + (model.Alcohol.toString().toDouble() * cup_ratio)
                            var caffeineCounter = args.coffeineProgress + (model.Caffeine.toString().toDouble() * cup_ratio)
                            var magnesiumCounter = args.magnesiumProgress + (model.Magnesium.toString().toDouble() * cup_ratio)
                            var potassiumCounter = args.potassiumProgress + (model.Potassium.toString().toDouble() * cup_ratio)
                            var sodiumCounter = args.sodiumProgress + (model.Sodium.toString().toDouble() * cup_ratio)
                            var proteinCounter = args.proteinProgress + (model.Protein.toString().toDouble() * cup_ratio)
                            var calciumCounter = args.calciumProgress + (model.Calcium.toString().toDouble() * cup_ratio)
                            var vitaminsCounter = args.vitaminsProgress + (model.Vitamins.toString().toDouble() * cup_ratio)

                            var currentDay = calendar.get(DAY_OF_WEEK)
                            val user_counter = UserCounter(caloriesCounter, sugarCounter, alcoholCounter, caffeineCounter, potassiumCounter, sodiumCounter,
                                proteinCounter, calciumCounter, vitaminsCounter, magnesiumCounter, progress, user_id, currentDay)
                            myRef.child("Users").child(user_id).child("UserCounter").setValue(user_counter)

                        var time = args.time
                        val user_drinks_history = UserDrinksHistory(model.Name.toString(), args.drinkSize.toDouble(), time.toInt())
                        myRef.child("Users").child(user_id).child("UserDrinksHistory").child(time.toString()).setValue(user_drinks_history)

                        val action = ItemFragmentDrinkDirections.actionItemFragmentDrink2ToNavigationHome7()
                        Navigation.findNavController(view).navigate(action)
                    })

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeveragesHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_item_drink, parent, false)
                    return BeveragesHolder(view)
                }

            }
        FirebaseAdapter.startListening()

        recyclerView.layoutManager = GridLayoutManager(context, columnCount)
        recyclerView.adapter = FirebaseAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Choose your beverage"
    }

    class BeveragesHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val container: View = mView.rootView
        val drinkAddName: TextView = mView.findViewById(R.id.drinkAdd_name)
    }
}