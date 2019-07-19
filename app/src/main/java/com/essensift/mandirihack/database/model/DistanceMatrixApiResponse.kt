package com.essensift.mandirihack.database.model

data class DistanceMatrixApiResponse(
    var rows: ArrayList<Rows>,
    val destination_addresses: ArrayList<String>,
    val origin_addresses: ArrayList<String>
) {

    constructor() : this(ArrayList(), ArrayList(), ArrayList())

    data class Rows(var elements: ArrayList<Element>) {
        constructor() : this(ArrayList())
    }

    data class Element(
        var distance: Distance,
        var duration: Duration
    ) {
        constructor() : this(Distance(), Duration())
    }

    data class Distance(
        var text: String,
        var value: Long
    ) {
        constructor() : this("", 0L)
    }

    data class Duration(
        var text: String,
        var value: Long
    ) {
        constructor() : this("", 0L)
    }
}
