package com.example.apptoaidcollegestudent.screens.LoanScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.model.LoanData
import com.example.apptoaidcollegestudent.screens.taskManager.TopBar
import com.example.apptoaidcollegestudent.utils.BottomNavigationBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StudentLoanScreen(navController : NavHostController){
    val listData = listOf<LoanData>(
        LoanData("State Bank Of India",
                "1.5% - 2.5%",
        "upTo 2.5%",
        "1-5 Years"),
        LoanData("Axis Bank",
            "5.5% - 10.5%",
            "upTo 1.5%",
            "1-5 Years"),
        LoanData("HDFC Bank",
            "3.5% - 2.5%",
            "upTo 6.5%",
            "10-15 Years"),
        LoanData("Punjab NAtional Bank",
            "1.5% - 7.5%",
            "upTo 7.5%",
            "1-15 Years"),
    )
    Scaffold(topBar = { TopBar(text = "STUDENT LOAN")},
    bottomBar = { BottomNavigationBar(navController = navController) }) {
        Surface(modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEFE6DD)) {
            LazyColumn{
                items(items = listData){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start) {
                            BuildText(title = "Bank Name", description = it.bankName)
                            BuildText(title = "Rate Of interest", description = it.roiRange)
                            BuildText(title = "Processing Fee", description = it.processingFee)
                            BuildText(title = "Tenure Range", description = it.tenureRange)
                            //Text(text = "Description: ${it.item?.whocanapply.toString()}")
                        }

                    }
                }
            }
        }

    }

}

@Composable
fun BuildText(title : String,
description : String){
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        ){
            append("$title: ")
        }
        withStyle(style = SpanStyle(
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        ){
            append(description)
        }


    }, modifier = Modifier.padding(10.dp))
}
