package com.example.apptoaidcollegestudent.network

import com.example.apptoaidcollegestudent.model.Quotes
import com.example.quotestakingapp.model.Quote
import retrofit2.http.GET

interface QuoteApi {

    @GET("quotes?author=albert-einstein")
    suspend fun getQuote() : Quotes
}