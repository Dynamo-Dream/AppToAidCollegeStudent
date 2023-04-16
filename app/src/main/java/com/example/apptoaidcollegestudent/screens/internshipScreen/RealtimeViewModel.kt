package com.example.apptoaidcollegestudent.screens.internshipScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptoaidcollegestudent.model.MedicalData
import com.example.apptoaidcollegestudent.model.Message
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse2
import com.example.shayadfinaldatabase.FirebaseRealtimeDB.repository.RealtimeRepository
import com.example.shayadfinaldatabase.utils.ResultState
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repo : RealtimeRepository
) : ViewModel() {
    private val _res : MutableState<ItemState> = mutableStateOf(ItemState())
    val res : State<ItemState> = _res
    private val _med : MutableState<ItemState2> = mutableStateOf(ItemState2())
    val med : State<ItemState2> = _med

    fun insertMessage(item : Message) = repo.insertMessage(item)
    fun insertMedicalData(item : MedicalData) = repo.insertMedicalData(item)
    fun delete(key : String) = repo.delete(key)
    fun getUtilData(){
        viewModelScope.launch {
            repo.getUtilData().collect{
                when(it){
                    is ResultState.Success ->{
                        _med.value = ItemState2(
                            item = it.data
                        )
                    }
                    is ResultState.Failure ->{
                        _med.value = ItemState2(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading ->{
                        _med.value = ItemState2(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
    init {
        viewModelScope.launch {
            repo.getItems().collect{
                when(it){
                    is ResultState.Success ->{
                        _res.value = ItemState(
                            item = it.data
                        )
                    }
                    is ResultState.Failure ->{
                        _res.value = ItemState(
                            error = it.msg.toString()
                        )
                    }
                    ResultState.Loading ->{
                        _res.value = ItemState(
                            isLoading = true
                        )
                    }
                }
            }
        }
        getUtilData()
    }

}

data class ItemState(
    val item : List<RealtimeModelResponse> = emptyList(),
    val error : String = "",
    val isLoading : Boolean = false
)
data class ItemState2(
    val item : List<RealtimeModelResponse2> = emptyList(),
    val error : String = "",
    val isLoading : Boolean = false
)