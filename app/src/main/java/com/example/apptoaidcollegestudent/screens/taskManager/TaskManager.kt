package com.example.apptoaidcollegestudent.screens.taskManager

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.example.apptoaidcollegestudent.utils.BottomNavigationBar
import com.example.apptoaidcollegestudent.utils.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskManager(navController: NavHostController) {

        Scaffold(
            topBar = { TopBar("TASK MANAGEMENT") },
            floatingActionButton = { FloatingButton() { navController.navigate(CollegeScreens.TaskInsertionScreen.name) } },
            isFloatingActionButtonDocked = false,
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }) {
            Surface(modifier = Modifier.fillMaxSize().background(Color(0xFFEFE6DD))) {
                HomeScreen(navController = navController)
            }


        }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: TaskViewModel = hiltViewModel(), navController: NavHostController) {
    val tasksList = viewModel.task.collectAsState().value

    Column() {
        TaskCard(
            color = Color(0xFFBB86FC),
            navController = navController,
            urgent1 = 4,
            importance1 = 4,
            urgent2 = 6,
            importance2 = 6
        )
        TaskCard(
            color = Color(0xFF03DAC5),
            text1 = "Schedule",
            text2 = "Do It Some Time Later",
            navController = navController,
            urgent1 = 0,
            importance1 = 4,
            urgent2 = 4,
            importance2 = 6
        )
        TaskCard(
            color = Color(0xFFBB86FC),
            text1 = "Delegate",
            text2 = "Ask Someone Else For It",
            navController = navController,
            urgent1 = 4,
            importance1 = 0,
            urgent2 = 6,
            importance2 = 4
        )
        TaskCard(
            color = Color(0xFF03DAC5),
            text1 = "Delete",
            text2 = "Doesn't Matter",
            navController = navController,
            urgent1 = 0,
            importance1 = 0,
            urgent2 = 4,
            importance2 = 4
        )

    }


}

@Composable
fun TopBar(text: String = "DASHBOARD") {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        elevation = 5.dp
    ) {
        Text(
            text, modifier = Modifier.padding(start = 20.dp),
            color = Color.White
        )

    }
}

@Composable
fun FloatingButton(onPress: () -> Unit = {}) {
    FloatingActionButton(
        onClick = { onPress.invoke() },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.Yellow
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "",
            tint = Color.Black
        )

    }
}
@Composable
fun TaskCard(
    text1: String = "Do it Now",
    text2: String = "It is Very Important",
    color: Color = Color.Red,
    navController: NavHostController,
    urgent1: Int,
    importance1: Int,
    urgent2: Int,
    importance2: Int
) {

    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height / 5)
            .padding(15.dp)
            .clickable {
                navController.navigate(
                    CollegeScreens.TaskAdditionScreen.name + "/$urgent1/$importance1/$urgent2/$importance2"
                )
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xFFFEE3D4),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = text1,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = text2,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }


        }

    }


}




