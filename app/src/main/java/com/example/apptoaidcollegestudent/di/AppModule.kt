package com.example.apptoaidcollegestudent.di

import android.content.Context
import androidx.room.Room
import com.example.apptoaidcollegestudent.network.QuoteApi
import com.example.apptoaidcollegestudent.utils.Constants
import com.example.einsen.database.TaskDatabase
import com.example.einsen.database.TaskDatabaseDao
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuoteApi() : QuoteApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context) : TaskDatabase
            = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "Task_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase) : TaskDatabaseDao = taskDatabase.taskDao()

    @Provides
    @Singleton
    fun providesRealtimeDb() : DatabaseReference =
        Firebase.database.reference
}