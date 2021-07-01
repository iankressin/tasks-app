package com.unisul.tasklist.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import com.unisul.tasklist.enums.PriorityTypes
import java.lang.Error
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun stringToPriorityType(string: String): PriorityTypes {
    when(string) {
        "Alta" -> return PriorityTypes.HIGH
        "Media" -> return PriorityTypes.MEDIUM
        "Baixa" -> return PriorityTypes.LOW
        else -> throw Error("The priority type is not defined")
    }
}

fun stringToDate(string: String): LocalDate {
    var parsedDate = LocalDate.parse(string, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    return parsedDate
}

fun dateToString(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
}