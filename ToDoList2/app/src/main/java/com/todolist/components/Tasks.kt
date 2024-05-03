package com.todolist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todolist.R
import com.todolist.data.Task

@Composable
fun Tasks(index: Int, _toDoList: MutableState<List<Task>>) {

    val editDialog = remember { mutableStateOf(false) }
    val deleteTaskDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(if (index % 2 == 0) Color(0xFF635994) else Color(0xFF7F5894))
            .padding(10.dp)
    ) {
        Row {
            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(text = _toDoList.value[index].title, color = Color.White,
                    fontSize = 20.sp,)
                Divider(color = Color.Black, modifier = Modifier.padding(5.dp))
                Text(text = _toDoList.value[index].description, color = Color.White,
                    fontSize = 16.sp, modifier = Modifier.padding(bottom = 5.dp))
            }
            Column(
                modifier = Modifier.weight(0.2f),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {

                IconButton(
                    onClick = { editDialog.value = true },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pen_ic),
                        contentDescription = "Delete Task"
                    )
                }
                IconButton(
                    onClick = { deleteTaskDialog.value = true},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_ic),
                        contentDescription = "Delete Task"
                    )
                }

            }
        }
        if(deleteTaskDialog.value){
            DeleteTask(index = index, _toDoList = _toDoList, deleteTaskDialog = deleteTaskDialog)
        }
        if(editDialog.value){
            EditTask(index = index, _toDoList = _toDoList, editDialog = editDialog)
        }
    }
}


@Composable
fun DeleteTask(index: Int, _toDoList: MutableState<List<Task>>, deleteTaskDialog: MutableState<Boolean>){
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Task") },
        text = { Text("Are you sure you want to delete this task?") },
        confirmButton = {
            TextButton(
                onClick = {
                    _toDoList.value = _toDoList.value.toMutableList().also { it.removeAt(index) }
                    deleteTaskDialog.value = false
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    deleteTaskDialog.value = false
                }
            ) {
                Text("No")
            }
        }
    )
}

@Composable
fun EditTask(index: Int, _toDoList: MutableState<List<Task>>, editDialog: MutableState<Boolean>) {

    val newTitle = remember { mutableStateOf(_toDoList.value[index].title) }
    val newDescription = remember { mutableStateOf(_toDoList.value[index].description) }

    if(editDialog.value){
        AlertDialog(
            onDismissRequest = { editDialog.value = false },
            title = { Text("Edit Task") },
            text = {
                Column {
                    TextField(
                        value = newTitle.value,
                        onValueChange = { newTitle.value = it },
                        label = { Text("Title") }
                    )
                    TextField(
                        value = newDescription.value,
                        onValueChange = { newDescription.value = it },
                        label = { Text("Description") },
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val listCopy = _toDoList.value.toMutableList() // Paso 1
                        listCopy[index] = Task(newTitle.value, newDescription.value) // Paso 2
                        _toDoList.value = listCopy // Paso 3
                        editDialog.value = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        editDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}


@Preview
@Composable
private fun PreviewTasks() {
    val _toDoList = remember { mutableStateOf(listOf<Task>()) }
    _toDoList.value += Task("Task 1", "Description 1")

    Tasks(0, _toDoList = _toDoList)
}