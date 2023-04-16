package com.example.apptoaidcollegestudent.repository

import android.util.Log
import com.example.apptoaidcollegestudent.network.QuoteApi
import com.example.quotestakingapp.model.DataorException
import com.example.quotestakingapp.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val api : QuoteApi
) {

    private val dataorException = DataorException<List<com.example.apptoaidcollegestudent.model.Result>,
            Boolean,
            Exception>()

    suspend fun getQuote() : DataorException<List<com.example.apptoaidcollegestudent.model.Result>,
            Boolean,
            Exception>{
        try {
            dataorException.loading = true
            dataorException.data = api.getQuote().results
            if(dataorException.data.toString().isNotEmpty()) dataorException.loading = false


        }catch (exception: Exception){
            dataorException.e = exception
            Log.d("exceprion", "getAllQuestion : ${dataorException.e!!.localizedMessage}")
        }

        return dataorException

    }
}

