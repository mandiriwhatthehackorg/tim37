package com.essensift.mandirihack.database.model

import java.io.Serializable

data class Emoney(
    var id: String,
    var name: String,
    var saldo: Long
) : Serializable {

    constructor() : this("", "", 0L)
}