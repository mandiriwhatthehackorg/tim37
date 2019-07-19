package com.essensift.mandirihack.database.model

data class ReverseGeocodingResponse(var results: ArrayList<Result>) {

    constructor() : this(ArrayList())

    data class Result(var formatted_address: String) {
        constructor() : this("")
    }

}