package com.example.myprofile.domain.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.DetailedTransactionDomain
import kotlinx.coroutines.flow.Flow

interface DetailedTransactionRepository {

    suspend fun getTransactionById(id: Int): Flow<Resource<DetailedTransactionDomain>>

}