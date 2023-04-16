package com.example.apptoaidcollegestudent.utils

import android.text.Layout.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.R
import com.example.apptoaidcollegestudent.navigation.CollegeScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun TopBar(text : String = "COLLEGE APP",navController: NavHostController){
    val mAuth = FirebaseAuth.getInstance()
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Text(text = text, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(200.dp))

        Icon(
            painterResource(id = R.drawable.address), contentDescription = "",
        modifier = Modifier
            .clickable {
                mAuth.signOut().run {
                    navController.navigate(CollegeScreens.AuthScreen.name)
                }

            }
            .size(35.dp))
    }
}

@Composable
fun BottomNavigationBar(navController : NavHostController){
    BottomNavigation() {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = { navController.navigate(CollegeScreens.HomeScreen.name) }, modifier = Modifier.size(50.dp)) {
                Icon(painter = painterResource(id = R.drawable.briefcase), contentDescription = "", modifier = Modifier.size(30.dp))

            }
            IconButton(onClick = { navController.navigate(CollegeScreens.InternshipScreen.name) }, modifier = Modifier.size(50.dp)) {
                Icon(painter = painterResource(id = R.drawable.bank), contentDescription = "", modifier = Modifier.size(30.dp))

            }
            IconButton(onClick = { navController.navigate(CollegeScreens.MedicalHelpScreen.name) }, modifier = Modifier.size(50.dp)) {
                Icon(painter = painterResource(id = R.drawable.doctor), contentDescription = "", modifier = Modifier.size(30.dp))

            }
            IconButton(onClick = { navController.navigate(CollegeScreens.TaskManagmentScreen.name) }, modifier = Modifier.size(50.dp)) {
                Icon(painter = painterResource(id = R.drawable.edit), contentDescription = "", modifier = Modifier.size(30.dp))

            }



        }
    }
}