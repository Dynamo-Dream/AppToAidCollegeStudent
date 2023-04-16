package com.example.apptoaidcollegestudent.screens.medicalScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.MyNotification
import com.example.apptoaidcollegestudent.model.MedicalData
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse
import com.example.apptoaidcollegestudent.screens.LoanScreen.BuildText
import com.example.apptoaidcollegestudent.screens.internshipScreen.RealtimeViewModel
import com.example.apptoaidcollegestudent.utils.BottomNavigationBar
import com.example.apptoaidcollegestudent.utils.TopBar
import com.example.einsen.screen.InputFieldText
import com.example.shayadfinaldatabase.utils.ResultState
import com.example.shayadfinaldatabase.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MedicalHelp(navController: NavHostController,viewModel: RealtimeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val isInsert = remember{
        mutableStateOf(false)
    }
    Scaffold(
        topBar = { TopBar(navController = navController, text = "MEDICAL/UTILITIES HELP") },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = {
            ExtendedFloatingActionButton(text = { Text(text = "ADD MEDICAL/UTILITIES HELP") },
                icon = { Icons.Default.Add }, onClick = {
                    isInsert.value = true

                })
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false,

        ) {
        val listData = viewModel.med.value.item
        val scope = rememberCoroutineScope()
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFE6DD))) {
            LazyColumn{
                items(items = listData){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                        elevation = 5.dp,
                        backgroundColor = Color(0xFFFEE3D4),
                    shape = RoundedCornerShape(25.dp)
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start) {
                            BuildText(title = "Name", description = it.item?.name.toString())
                            BuildText(title = "Hostel", description = it.item?.hostel.toString())
                            BuildText(title = "Room No.", description = it.item?.roomNo.toString())
                            BuildText(title = "Problem", description = it.item?.problem.toString())
                            BuildText(title = "Need", description = it.item?.need.toString())
                            BuildText(title = "Contact", description = it.item?.contact.toString())
                            Button(onClick = {
                                scope.launch(Dispatchers.Main) {
                                    viewModel.delete(it.key!!).collect {
                                        when (it) {
                                            is ResultState.Success -> {
                                                context.showMsg(
                                                    msg = it.data
                                                )

                                            }
                                            is ResultState.Failure -> {
                                                context.showMsg(
                                                    msg = it.msg.toString()
                                                )


                                            }
                                            ResultState.Loading -> {
                                                Log.d("TAG","asdasdas")
                                            }

                                        }
                                    }
                                }
                            }) {
                                Text(text = "ACCOMPLISHED")
                            }
                        }

                    }
                }
            }
            MyAlertDialog(isInsert = isInsert, viewModel = viewModel)
        }
        }



}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyAlertDialog(isInsert : MutableState<Boolean>,viewModel : RealtimeViewModel){

    val context = LocalContext.current
    val name = rememberSaveable {
        mutableStateOf("")
    }
    val hostel = rememberSaveable {
        mutableStateOf("")
    }
    val roomNo = rememberSaveable {
        mutableStateOf("")
    }
    val contact = rememberSaveable {
        mutableStateOf("")
    }
    val problem = rememberSaveable {
        mutableStateOf("")
    }
    val need = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    if (isInsert.value){
        AlertDialog(onDismissRequest = {isInsert.value = false},
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputFieldText(valueState = name, labelId = "Name")
                    InputFieldText(valueState = hostel, labelId = "Hostel")
                    InputFieldText(valueState = roomNo, labelId = "Room No")
                    InputFieldText(valueState = contact, labelId = "Contact")
                    InputFieldText(valueState = problem, labelId = "Problem")
                    InputFieldText(valueState = need, labelId = "Need",
                    imeAction = ImeAction.Done,
                    onAction = KeyboardActions(
                        onDone ={
                            if (keyboardController != null) {
                                keyboardController.hide()
                            }
                            return@KeyboardActions
                        }

                    )
                    )
                }
            },
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.insertMedicalData(
                                MedicalData(name.value,
                                    hostel.value,
                                    roomNo.value,
                                    contact.value,
                                    problem.value,
                                    need.value)
                            ).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        context.showMsg(
                                            msg = it.data
                                        )
                                        isInsert.value = false
                                        val notish = MyNotification(context, "HELP ON THE WAY", need.value)
                                        notish.FireNotification()
                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(
                                            msg = it.msg.toString()
                                        )
                                        isInsert.value = false

                                    }
                                    ResultState.Loading -> {
                                        Log.d("TAGG","")
                                    }

                                }
                            }
                        }
                    }) {
                        Text(text = "POST REQUEST")
                    }

                }

            })
    }

}
