package com.example.hydrate.database

class UserDrinksHistory {

    var drinkName: String? = "Name"
    var cupSize: Double? = 0.0
    var time: Int? = 0

    constructor() {

    }

    constructor( drinkname: String?, cupsize: Double?, time_: Int?) {
        this.drinkName = drinkname
        this.cupSize = cupsize
        this.time = time_
    }
}