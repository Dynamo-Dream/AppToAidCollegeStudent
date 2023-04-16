package com.example.apptoaidcollegestudent.screens.authScreen

import android.text.style.IconMarginSpan
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.apptoaidcollegestudent.navigation.CollegeScreens

@Composable
fun LoginScreen(navController: NavHostController,
                      viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginPage = rememberSaveable{
        mutableStateOf(true)
    }
    val context = LocalContext.current
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp),
    color = Color(0xFFEFE6DD)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ReaderTag()
            Spacer(modifier = Modifier.width(5.dp))
            if (showLoginPage.value){

                UserForm(){email, password ->
                    viewModel.SignInWithEmailAndPassword(email,password,context){
                        navController.navigate(CollegeScreens.HomeScreen.name)
                    }
                }
            }
            else{
                Text(text = "Please Put Password More Than 6 Words")
                UserForm(isCreateAccount = true){email, password ->
                    viewModel.CreateUserWithEmailAndPassword(email,password,context){
                        navController.navigate(CollegeScreens.HomeScreen.name)
                    }

                }
            }

        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val text = if(showLoginPage.value) "SignUp" else "Login"
            val text2 = if (showLoginPage.value) "New User" else "Already HAve an Account"
            Text(text = text2)
            Text(text = text,
                modifier = Modifier
                    .clickable { showLoginPage.value = !showLoginPage.value }
                    .padding(start = 5.dp),
                color = MaterialTheme.colors.secondaryVariant)

        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading : Boolean = false,
    isCreateAccount : Boolean = false,
    onDone : (String,String) -> Unit = {email,pwd ->}
) {
    val email = rememberSaveable { mutableStateOf<String>("") }
    val password = rememberSaveable { mutableStateOf<String>("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf<Boolean>(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value , password.value){
        email.value.isNotEmpty() && password.value.isNotEmpty()
    }

    EmailField(emailState = email, enabled = !loading, onAction = KeyboardActions{
        passwordFocusRequest.requestFocus()
    })
    PasswordField(passwordState = password,passwordVisibility = passwordVisibility,
        modifier = Modifier.focusRequester(passwordFocusRequest),enabled = !loading,
        onAction = KeyboardActions{
            if(!valid)
                return@KeyboardActions

            onDone(email.value.trim() ,password.value.trim())
        })

    SubmitButton(
        textid = if(isCreateAccount) "Create Account" else "Submit",
        loading = loading,
        validInputs = valid
    ){
        onDone(email.value.trim(),password.value.trim())
        keyboardController?.hide()
    }
}

@Composable
fun SubmitButton(textid: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textid, modifier = Modifier.padding(5.dp))

    }

}
@Composable
fun ReaderTag() {
    Text(text = "Authentication", fontSize = 35.sp,
        color = Color.Blue, fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun PasswordField(passwordState: MutableState<String>,
                  passwordVisibility: MutableState<Boolean>,
                  labelId : String = "Password",
                  modifier: Modifier,
                  enabled: Boolean,
                  imeAction : ImeAction = ImeAction.Next,
                  onAction: KeyboardActions = KeyboardActions.Default) {
    OutlinedTextField(value = passwordState.value,
        onValueChange = { passwordState.value = it},
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Password),
        label = { Text(text = labelId) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSecondary,
            fontSize = 18.sp
        ),
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "")},
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) }
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close

    }

}
@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    emailState : MutableState<String>,
    labelId : String = "Email",
    enabled : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState  = emailState,
        labelId = labelId,
        enabled  = enabled,
        imeAction = imeAction,
        onAction  = onAction,
        keyboardType = KeyboardType.Email
    )

}
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState : MutableState<String>,
    labelId : String = "Email",
    enabled : Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text
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
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription ="" )},
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    )

}
