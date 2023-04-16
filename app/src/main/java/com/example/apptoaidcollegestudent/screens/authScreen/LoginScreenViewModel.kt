package com.example.apptoaidcollegestudent.screens.authScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch


class LoginScreenViewModel : ViewModel() {

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> =  _loading
    fun SignInWithEmailAndPassword(email : String,password : String, context: Context,home : () -> Unit) =
        viewModelScope.launch {
            try{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            Log.d("TAG", "signWithEmailandPasssword : ${task.result.toString()}")
                            home()
                        }else{
//                            Log.d("TAG", "signWithEmailandPasssword : ${task.result.toString()}")
                            Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

                    }

            }catch (e : FirebaseAuthException){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }catch (e: FirebaseAuthInvalidUserException){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }catch (e: FirebaseAuthInvalidCredentialsException){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }catch (e:FirebaseException){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }catch (e:FirebaseAuthEmailException){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }catch (e:Exception){
                Toast.makeText(context,"Incorrect Email or Password",Toast.LENGTH_LONG).show()

            }
        }
    fun CreateUserWithEmailAndPassword(email: String, password: String,context: Context, home: () -> Unit){
        if (_loading.value == false){
            _loading.value = true
            val displayName = email?.split('@')?.get(1)
            if (displayName == "iiitranchi.ac.in"){
                if (password.length < 6){
                    Toast.makeText(context,"Password is too small",Toast.LENGTH_LONG).show()
                }else{
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful){
                                home()
                            }
                            else{
                                Toast.makeText(context,"Failed To Create Account",Toast.LENGTH_LONG).show()

                                // Log.d("TAG", "signWithEmailandPasssword : ${task.result.toString()}")
                            }
                        }
                }

            }else{
                Toast.makeText(context,"Please Put valid Email Id",Toast.LENGTH_LONG).show()
            }

            _loading.value = false
        }

    }

    }
