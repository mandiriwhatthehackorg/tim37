package com.essensift.mandirihack.database.model

import java.io.Serializable

data class FiestaPoint(
    var id: String,
    var timestamp: Long,
    var amount: Long,
    var sourceName: String
) : Serializable {

    constructor() : this("", 0L, 0L, "")
}