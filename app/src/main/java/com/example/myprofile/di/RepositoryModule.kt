package com.example.myprofile.di

import com.example.myprofile.data.repository.CourseRepositoryImpl
import com.example.myprofile.data.repository.DetailedTransactionRepositoryImpl
import com.example.myprofile.data.repository.TransactionsRepositoryImpl
import com.example.myprofile.data.repository.WalletsRepositoryImpl
import com.example.myprofile.domain.repository.CourseRepository
import com.example.myprofile.domain.repository.DetailedTransactionRepository
import com.example.myprofile.domain.repository.TransactionsRepository
import com.example.myprofile.domain.repository.WalletsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionsRepository(
        transactionsRepositoryImpl: TransactionsRepositoryImpl,
    ): TransactionsRepository

    @Binds
    @Singleton
    abstract fun bindDetailedTransactionRepository(
        detailedTransactionRepositoryImpl: DetailedTransactionRepositoryImpl,
    ): DetailedTransactionRepository

    @Binds
    @Singleton
    abstract fun bindWalletsRepository(
        walletsRepositoryImpl: WalletsRepositoryImpl
    ): WalletsRepository

    @Binds
    @Singleton
    abstract fun bindCourseRepository(
        courseRepositoryImpl: CourseRepositoryImpl
    ): CourseRepository
}