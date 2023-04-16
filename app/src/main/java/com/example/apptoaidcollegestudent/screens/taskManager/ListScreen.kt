package com.example.einsen.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apptoaidcollegestudent.screens.taskManager.TaskViewModel
import com.example.einsen.database.Task

@Composable
fun ListScreen(
    urgent1 : Int,
    importance1 : Int,
    urgent2 : Int,
    importance2 : Int,
    viewModel: TaskViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
){
    val listOfItems = viewModel.allTask.collectAsState().value
    Log.d("TAGGG", listOfItems.toString())
    val itemsList = listOfItems.filter { it.urgentRating>=urgent1 && it.importanceRating >=importance1
            && it.urgentRating<urgent2 && it.importanceRating<importance2}
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn{
            items(items = itemsList){task ->
                TaskListCard(viewModel = viewModel,task = task)
            }
        }

    }

}

@Composable
fun TaskListCard(viewModel: TaskViewModel,task: Task){
    
    val checkedState = remember {
        mutableStateOf(false)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkedState.value,
            modifier = Modifier.padding(16.dp),
            onCheckedChange ={
                checkedState.value = it
                if (checkedState.value){
                    viewModel.delete(task)
                }
            } )
        Card(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {

            },
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            elevation = 10.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {

                Column() {
                    Text(text = task.task, fontSize = 20.sp, modifier = Modifier.padding(10.dp))
                    Text(text = "Deadline: ${task.date}", style = MaterialTheme.typography.body1, modifier = Modifier.padding(10.dp))

                    AnimatedVisibility(visible = expanded) {
                        Column() {

                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    color = Color.DarkGray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                ){
                                    append("Description: ")
                                }
                                withStyle(style = SpanStyle(
                                    color = Color.DarkGray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light
                                )
                                ){
                                    append(task.description)
                                }


                            }, modifier = Modifier.padding(10.dp))
                        }
                    }
                    Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = "Down Arrow",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                expanded = !expanded
                            },
                        tint = Color.DarkGray)

                }
            }

        }
    }
    


}