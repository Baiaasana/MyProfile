package com.example.myprofile.data.remote.network

import com.example.myprofile.common.Constants
import com.example.myprofile.data.remote.model.TransactionsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.TRANSACTIONS_END_POINT)
    suspend fun getTransactions(@Query("page") page: Int): Response<TransactionsDTO>

}