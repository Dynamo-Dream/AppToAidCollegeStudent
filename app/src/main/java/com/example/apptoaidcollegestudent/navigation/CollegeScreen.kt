package com.example.apptoaidcollegestudent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apptoaidcollegestudent.screens.HomeScreen
import com.example.apptoaidcollegestudent.screens.LoanScreen.StudentLoanScreen
import com.example.apptoaidcollegestudent.screens.authScreen.LoginScreen
import com.example.apptoaidcollegestudent.screens.internshipScreen.InternshipScreen
import com.example.apptoaidcollegestudent.screens.internshipScreen.MessageUI
import com.example.apptoaidcollegestudent.screens.medicalScreen.MedicalHelp
import com.example.apptoaidcollegestudent.screens.splashScreen.SplashScreen
import com.example.apptoaidcollegestudent.screens.taskManager.TaskManager
import com.example.einsen.screen.InsertScreen
import com.example.einsen.screen.ListScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CollegeNavigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CollegeScreens.SplashScreen.name ){

        composable(route = CollegeScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(route = CollegeScreens.AuthScreen.name){
            LoginScreen(navController = navController)
        }
        composable(route = CollegeScreens.HomeScreen.name){
            if (FirebaseAuth.getInstance().currentUser != null){
                HomeScreen(navController)
            }else
            {
                navController.navigate(CollegeScreens.AuthScreen.name){
                    popUpTo(CollegeScreens.HomeScreen.name){
                        inclusive = true
                    }
                }

            }

        }
        composable(route = CollegeScreens.TaskManagmentScreen.name){
            TaskManager(navController)
        }
        composable(route = CollegeScreens.TaskInsertionScreen.name){
            InsertScreen(navController = navController)
        }
        composable(route = CollegeScreens.TaskAdditionScreen.name + "/{urgent1}/{importance1}/{urgent2}/{importance2}",
            arguments = listOf(
                navArgument(
                    name = "urgent1"
                ){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(
                    name = "importance1"
                ){
                    type = NavType.IntType
                    defaultValue = 0

                },
                navArgument(
                    name = "urgent2"
                ){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(
                    name = "importance2"
                ){
                    type = NavType.IntType
                    defaultValue = 0

                }
            )
        ){
            ListScreen(urgent1 = it.arguments!!.getInt("urgent1"),
                importance1 = it.arguments!!.getInt("importance1"),
                urgent2 = it.arguments!!.getInt("urgent2"),
                importance2 = it.arguments!!.getInt("importance2"),
                navController = navController)
        }
        composable(CollegeScreens.InternshipScreen.name){
            StudentLoanScreen(navController = navController)
        }
        composable(CollegeScreens.MessageUI.name + "/{projectName}",
            arguments = listOf(
                navArgument(
                    name = "projectName"
                ){
                    type = NavType.StringType
                    defaultValue = ""
                })){
                MessageUI(navController = navController, projectName = it.arguments!!.getString("projectName"))
            }
        composable(CollegeScreens.MedicalHelpScreen.name){
            MedicalHelp(navController)
        }
        }




    }

