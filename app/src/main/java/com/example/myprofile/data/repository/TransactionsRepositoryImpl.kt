package com.example.myprofile.data.repository

import com.example.myprofile.common.ResponseHandler
import com.example.myprofile.data.remote.network.ApiService
import com.example.myprofile.domain.model.TransactionsDomain
import com.example.myprofile.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : TransactionsRepository {

    override suspend fun getTransactions(page: Int): TransactionsDomain? {

//            val result = responseHandler.safeApiCall { apiService.getTransactions(page = page) }
//            when (result.status) {
//                Resource.Status.SUCCESS -> {
//                    val data = Resource.success(result.data!!.toDomain())
//                    d("log", "logR = ".plus(data))
//                }
//                Resource.Status.ERROR -> {
//                    return Resource.error(result.message.toString())
//                }
//                Resource.Status.LOADING -> {
//                    return Resource.loading(null)
//                }
//            }

        val response = apiService.getTransactions(page = page)
        if (response.isSuccessful) {
            return response.body()!!.toDomain()
        }else
            return null
    }

}
