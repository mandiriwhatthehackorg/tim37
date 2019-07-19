package com.essensift.mandirihack.database.model

import java.io.Serializable

data class GeoLoc(
    var latitude: Double,
    var longitude: Double
) : Serializable {

    constructor() : this(0.0, 0.0)
}