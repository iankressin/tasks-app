package com.unisul.tasklist.helpers

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.unisul.tasklist.enums.PriorityTypes
import java.lang.Error
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun stringToPriorityType(string: String): PriorityTypes {
    when(string) {
        "Alta" -> return PriorityTypes.HIGH
        "Media" -> return PriorityTypes.MEDIUM
        "Baixa" -> return PriorityTypes.LOW
        else -> throw Error("The priority type is not defined")
    }
}

fun stringToDate(string: String): LocalDate {
    try {
        var parsedDate = LocalDate.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        return parsedDate
    } catch (err: Error) {
        println("Something went wrong")

        throw err
    }
}

fun dateToString(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
}

fun isDateValid(dateString: String): Boolean {
//    println(dateString.length)
    if (dateString.length < 10) {
        return false
    }

    val date = stringToDate(dateString)

    if (date.isBefore(LocalDate.now())) {
        return false
    }

    return true
}