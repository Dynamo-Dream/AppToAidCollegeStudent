package com.example.apptoaidcollegestudent.screens.Quote

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptoaidcollegestudent.repository.QuoteRepository
import com.example.quotestakingapp.model.DataorException
import com.example.quotestakingapp.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuoteViewModel @Inject constructor(private val repo : QuoteRepository) : ViewModel() {

    val data : MutableState<DataorException<List<com.example.apptoaidcollegestudent.model.Result>,Boolean,Exception>> =
        mutableStateOf(DataorException(null,true,Exception("")))

    init {
        getAllQuestion()
    }
    fun getAllQuestion(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = repo.getQuote()
            if (data.value.toString().isNotEmpty()) data.value.loading = false
        }

    }
}