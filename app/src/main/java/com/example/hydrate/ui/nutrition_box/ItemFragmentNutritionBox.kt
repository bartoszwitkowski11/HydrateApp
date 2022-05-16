package com.example.hydrate.ui.nutrition_box

//import com.example.hydrate.database.Beverages as Beverages

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hydrate.R
import com.example.hydrate.R.id
import com.example.hydrate.R.id.drinkList
import com.example.hydrate.R.layout.fragment_item_nutrition_box
import com.example.hydrate.R.layout.fragment_item_nutrition_box_list
import com.example.hydrate.database.Beverages
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase


/**
 * A fragment representing a list of Items.
 */
class ItemFragmentNutritionBox : Fragment() {

    lateinit var recyclerView : RecyclerView
    val firebase = FirebaseDatabase.getInstance("https://hydrate-b5027-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.Settings) {
            findNavController().navigate(R.id.action_navigation_nutrition_box_to_settingsFragment2)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(fragment_item_nutrition_box_list, container, false)
        setHasOptionsMenu(true)
        recyclerView = view.findViewById(drinkList)

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
                    holder.drinkName.text = model.Name.toString()
                    holder.drinkAlcohol.text = "Alcohol: " + model.Alcohol.toString()  + " ml"
                    holder.drinkCalories.text = "Calories: " + model.Calories.toString()  + " kcal"
                    holder.drinkCaffeine.text = "Caffeine: " + model.Caffeine.toString()  + " mg"
                    holder.drinkMagnesium.text = "Magnesium: " + model.Magnesium.toString()  + " mg"
                    holder.drinkSugar.text = "Sugar: " + model.Sugar.toString()  + " mg"
                    holder.drinkPotassium.text = "Potassium: " + model.Potassium.toString()  + " mg"
                    holder.drinkSodium.text = "Sodium: " + model.Sodium.toString()  + " mg"
                    holder.drinkProtein.text = "Protein: " + model.Protein.toString()  + " mg"
                    holder.drinkCalcium.text = "Calcium: " + model.Calcium.toString()  + " mg"
                    holder.drinkVitamins.text = "Vitamins: " + model.Vitamins.toString() + " mg"
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeveragesHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(fragment_item_nutrition_box, parent, false)
                    return BeveragesHolder(view)
                }

            }
        FirebaseAdapter.startListening()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FirebaseAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    class BeveragesHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val container: View = mView.rootView
        val drinkName: TextView = mView.findViewById(id.drink_name)
        val drinkSugar : TextView = mView.findViewById(id.drink_sugar)
        val drinkAlcohol : TextView = mView.findViewById(id.drink_alcohol)
        val drinkCalories : TextView = mView.findViewById(id.drink_calories)
        val drinkCaffeine : TextView = mView.findViewById(id.drink_caffeine)
        val drinkMagnesium : TextView = mView.findViewById(id.drink_magnesium)
        val drinkPotassium : TextView = mView.findViewById(id.drink_potassium)
        val drinkSodium : TextView = mView.findViewById(id.drink_sodium)
        val drinkProtein : TextView = mView.findViewById(id.drink_protein)
        val drinkCalcium : TextView = mView.findViewById(id.drink_calcium)
        val drinkVitamins : TextView = mView.findViewById(id.drink_vitamins)
    }
}


