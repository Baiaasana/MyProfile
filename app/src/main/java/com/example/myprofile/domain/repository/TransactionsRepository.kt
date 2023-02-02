package com.example.myprofile.domain.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.TransactionsDomain

interface TransactionsRepository {

    suspend fun getTransactions(page: Int): Resource<TransactionsDomain>

}