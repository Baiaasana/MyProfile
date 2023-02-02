package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.TransactionsDomain
import com.example.myprofile.domain.repository.TransactionsRepository
import javax.inject.Inject

//class TransactionsUseCase @Inject constructor(private val repository: TransactionsRepository){
//
//    suspend fun invoke(page: Int) : Resource<TransactionsDomain>{
//        return repository.getTransactions(page = page)
//    }
//}