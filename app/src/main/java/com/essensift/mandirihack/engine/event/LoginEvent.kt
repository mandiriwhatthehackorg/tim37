package com.essensift.mandirihack.engine.event

import java.io.Serializable

data class LoginEvent(
    var data: Serializable,
    var param: String
) : Serializable