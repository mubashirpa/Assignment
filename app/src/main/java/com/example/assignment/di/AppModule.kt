package com.example.assignment.di

import com.example.assignment.core.Constants
import com.example.assignment.data.remote.service.AssignmentService
import com.example.assignment.data.repository.CharchaRepositoryImpl
import com.example.assignment.domain.repository.CharchaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAssignmentService(): AssignmentService {
        return Retrofit.Builder()
            .baseUrl(Constants.ASSIGNMENT_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AssignmentService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharchaRepository(assignmentService: AssignmentService): CharchaRepository =
        CharchaRepositoryImpl(assignmentService)
}