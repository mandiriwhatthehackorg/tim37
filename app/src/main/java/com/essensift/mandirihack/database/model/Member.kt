package com.essensift.mandirihack.database.model

import java.io.Serializable

data class Member(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var saldo: Long
) : Serializable {

    constructor() : this("", "", "", "", 0L)
}