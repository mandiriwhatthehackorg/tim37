package com.essensift.mandirihack.database.model

import java.io.Serializable

data class Transaction(
    var id: String,
    var timestamp: Long,
    var amount: Long,
    var originAccount: String,
    var originChannel: String,
    var originName: String,
    var destinationAccount: String,
    var destinationChannel: String,
    var destinationName: String
) : Serializable {

    constructor() : this("", 0L, 0L, "", "", "", "", "", "")
}