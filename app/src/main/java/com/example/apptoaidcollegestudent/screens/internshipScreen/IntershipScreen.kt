package com.example.apptoaidcollegestudent.screens.internshipScreen

import android.annotation.SuppressLint

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.model.Message
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse
import com.example.apptoaidcollegestudent.screens.LoanScreen.BuildText


import com.example.einsen.screen.InputFieldText
import com.example.shayadfinaldatabase.utils.ResultState
import com.example.shayadfinaldatabase.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InternshipScreen(viewModel: RealtimeViewModel = hiltViewModel(),navController: NavHostController){

        Surface(modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEFE6DD)
        ) {

            val checker = remember {
                mutableStateOf("")
            }
            var expanded by remember { mutableStateOf(false) }
            val list = listOf("Android Development", "Game Development", "Machine Learning",
                "Artificial Intelligence", "Web Development", "Cyber Security", "Data Science")

            val icon = if (expanded) {
                Icons.Filled.KeyboardArrowUp
            } else {
                Icons.Filled.KeyboardArrowDown
            }
            var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = checker.value,
                        onValueChange = { checker.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        label = { Text(text = "Filter") },
                        trailingIcon = {
                            Icon(icon, "", Modifier.clickable { expanded = !expanded })
                        },
                        shape = RoundedCornerShape(50)
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    ) {

                        list.forEach { label ->
                            DropdownMenuItem(onClick = {
                                checker.value = label
                                expanded = false
                            }) {
                                Text(text = label)

                            }
                        }

                    }
                }
                val listData = viewModel.res.value.item
                var data = listOf<RealtimeModelResponse>()
                if (checker.value.isNotEmpty()){
                    data = listData.filter {
                        it.item?.skills == checker.value
                    }
                }else{
                    data = listData
                }

                LazyColumn{
                    items(items = data){
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                            elevation = 5.dp,
                        backgroundColor = Color.White,
                            shape = RoundedCornerShape(25.dp))
                        {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 18.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start) {
                                BuildText(title = "Project Title", description = it.item?.title.toString())
                                BuildText(title = "Description", description = it.item?.description.toString())
                                BuildText(title = "Skills Required", description = it.item?.skills.toString())
                                BuildText(title = "No. Of Openings", description = it.item?.noofopening.toString())
                                BuildText(title = "Who Can Apply", description = it.item?.whocanapply.toString())
                                BuildText(title = "Faculty", description = it.item?.nameofteacher.toString())
                                Button(onClick = { navController.navigate(CollegeScreens.MessageUI.name + "/${it.item?.title.toString()}") }) {
                                    Text(text = "APPLY")
                                }
                            }

                        }
                    }
                }
            }

        }
    }
