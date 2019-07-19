package com.labters.lottiealertdialoglibrary

interface ClickListener {
    fun onClick(dialog: LottieAlertDialog)
}

interface ClickListener2 {
    fun onClick(dialog: LottieAlertDialog, message: String?)
}