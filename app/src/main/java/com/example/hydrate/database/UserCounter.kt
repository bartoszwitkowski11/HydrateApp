package com.example.hydrate.database

data class UserCounter(
        var CaloriesCounter: Double? = 0.0,
        var SugarCounter: Double? = 0.0,
        var AlcoholCounter: Double? = 0.0,
        var CaffeineCounter: Double? = 0.0,
        var PotassiumCounter: Double? = 0.0,
        var SodiumCounter: Double? = 0.0,
        var ProteinCounter: Double? = 0.0,
        var CalciumCounter: Double? = 0.0,
        var VitaminsCounter: Double? = 0.0,
        var MagnesiumCounter: Double? = 0.0,
        var ProgressCounter: Double? = 0.0,
        var ID: String = "empty",
        var CurrentDay: Int? = 0
) {

}