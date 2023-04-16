package com.example.apptoaidcollegestudent.screens.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.R
import com.example.apptoaidcollegestudent.navigation.CollegeScreens


import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f)
                        .getInterpolation(it)
                }
            )
        )
        delay(2000)
 //
        //       navController.navigate(CollegeScreens.AuthScreen.name)
        if (FirebaseAuth.getInstance().currentUser?.email?.isNotEmpty() == true)
        {
            navController.navigate(CollegeScreens.HomeScreen.name)
        }
        else{
            navController.navigate(CollegeScreens.AuthScreen.name)
        }


    })
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.scale(scale.value)){
            Image(painter = painterResource(id = R.drawable.iiitranchilogo),
                contentDescription = "Splash Screen",
                modifier = Modifier.size(300.dp))

        }

    }
}