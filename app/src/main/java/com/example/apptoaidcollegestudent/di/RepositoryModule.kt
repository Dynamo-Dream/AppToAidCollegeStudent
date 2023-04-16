package com.example.apptoaidcollegestudent.di

import com.example.shayadfinaldatabase.FirebaseRealtimeDB.repository.RealtimeRepository
import com.example.shayadfinaldatabase.FirebaseRealtimeDB.repository.RealtimeRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealtimeRepository(
        repo : RealtimeRepositoryImplementation
    ) : RealtimeRepository
}