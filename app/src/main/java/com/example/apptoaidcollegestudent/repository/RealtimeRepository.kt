package com.example.shayadfinaldatabase.FirebaseRealtimeDB.repository

import com.example.apptoaidcollegestudent.model.MedicalData
import com.example.apptoaidcollegestudent.model.Message
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse
import com.example.apptoaidcollegestudent.repository.RealtimeModelResponse2
import com.example.shayadfinaldatabase.utils.ResultState
import kotlinx.coroutines.flow.Flow


interface RealtimeRepository {

    fun insertMessage(
        item : Message
    ) : Flow<ResultState<String>>

    fun insertMedicalData(
        item : MedicalData
    ) : Flow<ResultState<String>>

    fun getUtilData() : Flow<ResultState<List<RealtimeModelResponse2>>>

    fun getItems() : Flow<ResultState<List<RealtimeModelResponse>>>

    fun delete(
        key : String
    ): Flow<ResultState<String>>

}