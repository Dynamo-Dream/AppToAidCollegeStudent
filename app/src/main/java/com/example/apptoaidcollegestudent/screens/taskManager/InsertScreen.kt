package com.example.einsen.screen

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.screens.taskManager.TaskViewModel
import com.example.einsen.database.Task
import java.util.*

@Composable
fun InsertScreen(navController: NavHostController,passsingTask : (Task) -> Unit = {},viewModel: TaskViewModel = hiltViewModel()){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        color = Color(0xFFEFE6DD)) {

        val task = rememberSaveable {
            mutableStateOf("")
        }
        val description = rememberSaveable {
            mutableStateOf("")
        }
        val urgentRating = rememberSaveable {
            mutableStateOf(0)
        }
        val importanceRating = rememberSaveable {
            mutableStateOf(0)
        }

        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            }, mYear, mMonth, mDay
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
           // Text(text = "Task", fontSize = 20.sp)
            InputFieldText(valueState = task, labelId = "Task")
          //  Text(text = "Description" , fontSize = 20.sp)
            InputFieldText(valueState = description, labelId = "Description")
            Text(text = "Urgency", fontSize = 20.sp)
            Slider(
                value = urgentRating.value.toFloat(),
                onValueChange = { sliderValue_ ->
                    urgentRating.value = sliderValue_.toInt()
                },
                onValueChangeFinished = {
                    // this is called when the user completed selecting the value
                  //  Log.d("MainActivity", "sliderValue = $sliderValue")
                },
                valueRange = 0f..5f,
                steps = 6,
                colors = SliderDefaults.colors(Color.Black)
            )
           // InputFieldnumber(valueState = urgentRating, keyboardType = KeyboardType.Number, labelId = "Urgent")
            Text(text = "Importance", fontSize = 20.sp)
            Slider(
                value = importanceRating.value.toFloat(),
                onValueChange = { sliderValue_ ->
                    importanceRating.value = sliderValue_.toInt()
                },
                onValueChangeFinished = {
                    // this is called when the user completed selecting the value
                    //  Log.d("MainActivity", "sliderValue = $sliderValue")
                },
                valueRange = 0f..5f,
                steps = 6,
                colors = SliderDefaults.colors(Color.Black)
            )
            Text(text = "Deadline", fontSize = 20.sp)
            Button(onClick = {
                mDatePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
                Text(text = "Open Date Picker", color = Color.White)
            }
            Text(text = "Selected Date: ${mDate.value}", fontSize = 18.sp, textAlign = TextAlign.Center)

            Row(horizontalArrangement = Arrangement.End) {
                Button(onClick = {
//                    if (task.value.isNotEmpty() && description.value.isNotEmpty()
//                            ){
//
//                        passsingTask(
//                            Task(task = task.value,
//                        description = description.value,
//                        urgentRating = urgentRating.value,
//                        importanceRating = importanceRating.value))
//
//                    }
//
//                    else Log.d("ET","EMPTY VALUE")
                    val taskObserver:Task = Task(task = task.value,
                        description = description.value,
                        urgentRating = urgentRating.value,
                        importanceRating = importanceRating.value,
                    date = mDate.value)
                    Log.d("ET",taskObserver.toString())
                    viewModel.insert(Task(task = task.value,
                        description = description.value,
                        urgentRating = urgentRating.value,
                        importanceRating = importanceRating.value,
                    date = mDate.value))
                    task.value = ""
                    description.value = ""
                    urgentRating.value = 0
                    importanceRating.value = 0
                    mDate.value = ""

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(text = "SAVE TASK", fontWeight = FontWeight.SemiBold, fontSize = 23.sp,
                    color = Color.White)


                }
            }
        }
    }
}

@Composable
fun InputFieldText(
    modifier: Modifier = Modifier,
    valueState : MutableState<String>,
    labelId : String = "Email",
    enabled : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,

) {
    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        label = { Text(text = labelId) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSecondary,
            fontSize = 18.sp
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    )

}
@Composable
fun InputFieldnumber(
    modifier: Modifier = Modifier,
    valueState : MutableState<Int>,
    labelId : String = "Email",
    enabled : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    OutlinedTextField(value = valueState.value.toString(),
        onValueChange = {
                        valueState.value = it.toIntOrNull() ?: 0

        },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        label = { Text(text = labelId) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSecondary,
            fontSize = 18.sp
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    )

}
