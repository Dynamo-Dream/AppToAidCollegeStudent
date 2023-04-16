package com.example.apptoaidcollegestudent.screens.internshipScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.model.Message
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.example.apptoaidcollegestudent.screens.taskManager.TopBar
import com.example.einsen.screen.InputFieldText
import com.example.shayadfinaldatabase.utils.ResultState
import com.example.shayadfinaldatabase.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessageUI(projectName: String? = "", onClick : (Message) -> Unit = {}, viewModel: RealtimeViewModel = hiltViewModel(),
              navController: NavHostController
){
    val name = rememberSaveable {
        mutableStateOf("")
    }
    val batch = rememberSaveable {
        mutableStateOf("")
    }
    val rollno = rememberSaveable{
        mutableStateOf("")
    }
    val message = rememberSaveable{
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(topBar = { TopBar("MESSAGE")}) {
        Surface(modifier = Modifier.background(color = Color(0xFFEFE6DD))) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                InputFieldText(valueState = name, labelId = "Name")
                //  Text(text = "Description" , fontSize = 20.sp)
                InputFieldText(valueState = batch, labelId = "Batch")
                InputFieldText(valueState = rollno, labelId = "Roll No.")
                InputFieldText(valueState = message, labelId = "Pitch/Message")
                Button(onClick = { scope.launch(Dispatchers.Main) {
                    viewModel.insertMessage(
                        Message(projectName,
                            name.value,
                            batch.value,
                            rollno.value,
                            message.value)
                    ).collect {
                        when (it) {
                            is ResultState.Success -> {
                                context.showMsg(
                                    msg = it.data
                                )
                                navController.navigate(CollegeScreens.HomeScreen.name)

                            }
                            is ResultState.Failure -> {
                                context.showMsg(
                                    msg = it.msg.toString()
                                )


                            }
                            ResultState.Loading -> {
                                Log.d("TAGG","assdfsdf")
                            }

                        }
                    }
                } }) {
                    Text(text = "APPLY")
                }
            }
        }
    }

}