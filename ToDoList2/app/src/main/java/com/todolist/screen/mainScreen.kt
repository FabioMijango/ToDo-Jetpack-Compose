package com.todolist.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todolist.components.Tasks
import com.todolist.data.Task


@Composable
fun MainScreen() {

    val _toDoList = remember { mutableStateOf(listOf<Task>()) }
    val showAddTask = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(Color(0xFF6D81D2))
    ) {
        Log.i("ToDoList", _toDoList.toString())
        if (showAddTask.value) {
            AddTask(showAddTask = showAddTask, _toDoList = _toDoList)
        }

        Button(
            onClick = { showAddTask.value = true },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(Color(0xFF7F5894))
        ) {
            Text("Add Task")
        }

        Divider(color = Color.White, modifier = Modifier.padding(5.dp), thickness = 3.dp)

        Text(
            text = "To Do List",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight(500),
            modifier = Modifier.padding(10.dp)
        )

        LazyColumn (
            userScrollEnabled = true,
        ){
            itemsIndexed(_toDoList.value) { index, _ ->
                Tasks(index, _toDoList = _toDoList)
            }
        }
    }
}



@Composable
fun AddTask(showAddTask: MutableState<Boolean>, _toDoList: MutableState<List<Task>>) {

    val context = LocalContext.current

    val titleTask = remember { mutableStateOf("") }
    val descriptionTask = remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = { },
        title = { Text("Edit Task") },
        text = {
            Column {
                TextField(
                    value = titleTask.value,
                    onValueChange = { titleTask.value = it },
                    label = { Text("Title Task") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(300.dp),
                    singleLine = true
                )
                TextField(
                    value = descriptionTask.value,
                    onValueChange = { descriptionTask.value = it },
                    label = { Text("Description") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(300.dp)

                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (titleTask.value.isEmpty() || descriptionTask.value.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please fill all the fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@TextButton
                    }
                    _toDoList.value += Task(titleTask.value, descriptionTask.value)
                    titleTask.value = ""
                    descriptionTask.value = ""
                    showAddTask.value = false
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showAddTask.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    )

}


@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}