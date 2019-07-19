package com.essensift.mandirihack.database.model

import java.io.Serializable

data class Admin(
    var id: String,
    var name: String,
    var pin: String
) : Serializable {

    constructor() : this("", "", "")
}