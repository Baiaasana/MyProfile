package com.example.myprofile.data.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.common.ResponseHandler
import com.example.myprofile.data.remote.network.ApiService
import com.example.myprofile.domain.model.TransactionsDomain
import com.example.myprofile.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : TransactionsRepository {

    override suspend fun getTransactions(page: Int): Resource<TransactionsDomain> {

        return try {
            Resource.loading(null)
            val response = apiService.getTransactions(page)
            val result = response.body()!!
            if (response.isSuccessful) {
                Resource.success(result.toDomain())
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }
}
