package com.essensift.mandirihack.engine

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {

    private val cal = Calendar.getInstance()
    fun formatLongDateOnly(time: Long): String {
        cal.timeInMillis = time
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(cal.time)
    }

    fun formatLongDate(time: Long): String {
        cal.timeInMillis = time
        return SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault()).format(cal.time)
    }

    fun formatDateOnly(date: Date): String {
        cal.time = date
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(cal.time)
    }

    fun formatDate(date: Date): String {
        cal.time = date
        return SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault()).format(cal.time)
    }

    fun checkIfEpochisToday(t: Long): Boolean {
        val now = cal
        val timeToCheck = cal
        timeToCheck.timeInMillis = t

        return if (now.get(Calendar.YEAR) == timeToCheck.get(Calendar.YEAR)) {
            now.get(Calendar.DAY_OF_YEAR) == timeToCheck.get(Calendar.DAY_OF_YEAR)
        } else
            false
    }
}