package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.TransactionsDomain
import com.example.myprofile.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class TransactionsUseCase @Inject constructor(private val repository: TransactionsRepository){
//
//    suspend fun invoke(page: Int) : Flow<Resource<TransactionsDomain>>{
//        return repository.getTransactions(page = page)
//    }
//}