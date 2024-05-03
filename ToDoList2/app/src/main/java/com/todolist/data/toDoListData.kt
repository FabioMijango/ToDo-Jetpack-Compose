package com.todolist.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Task(
    val title: String,
    val description: String
) {
    override fun toString(): String {
        return "Task(title='$title', description='$description')"
    }
}

val toDoList: MutableState<List<Task>> = mutableStateOf(listOf())