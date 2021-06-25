package com.unisul.tasklist.models

import com.unisul.tasklist.enums.PriorityTypes
import java.time.LocalDate

class Task(val name: String, val description: String, val dueDate: LocalDate, val priority: PriorityTypes) {

}