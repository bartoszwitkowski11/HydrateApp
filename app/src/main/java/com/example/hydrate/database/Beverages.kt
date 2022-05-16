package com.example.hydrate.database

class Beverages {

    var Calories: Double? = 0.0
    var Sugar: Double? = 0.0
    var Alcohol: Double? = 0.0
    var Caffeine: Double? = 0.0
    var Magnesium: Double? = 0.0
    var Potassium: Double? = 0.0
    var Sodium: Double? = 0.0
    var Protein: Double? = 0.0
    var Calcium: Double? = 0.0
    var Vitamins: Double? = 0.0
    var Name: String? = "Name"

    constructor() {

    }

    constructor(
        calories: Double?, sugar: Double?, alcohol: Double?,
        caffeine: Double?, magnesium: Double?, potassium: Double?, sodium: Double?,
        protein: Double?, calcium: Double?, vitamins: Double?, name: String?) {
        //this.Beverages = beverages
        this.Calories = calories
        this.Sugar = sugar
        this.Alcohol = alcohol
        this.Caffeine = caffeine
        this.Magnesium = magnesium
        this.Potassium = potassium
        this.Sodium = sodium
        this.Protein = protein
        this.Calcium = calcium
        this.Vitamins = vitamins
        this.Name = name
    }
}