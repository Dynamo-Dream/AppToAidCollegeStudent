package com.example.apptoaidcollegestudent.screens

import android.annotation.SuppressLint
import android.text.Layout.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.example.apptoaidcollegestudent.screens.Quote.QuoteViewModel
import com.example.apptoaidcollegestudent.screens.internshipScreen.InternshipScreen
import com.example.apptoaidcollegestudent.utils.BottomNavigationBar
import com.example.apptoaidcollegestudent.utils.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController : NavHostController){
    Surface(modifier = Modifier.fillMaxSize(),
    color = Color(0xFFEFE6DD)) {
        Scaffold(topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }) {
            InternshipScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreenContent() {
    val viewModel : QuoteViewModel = hiltViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(20.dp), elevation = 10.dp) {
            Text(text = viewModel.data.value.data?.get(0)?.content.toString(),
            fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), elevation = 10.dp) {
            Text(text = viewModel.data.value.data?.get(0)?.author.toString(),
                fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

    }

}
