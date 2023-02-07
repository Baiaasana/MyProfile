package com.example.myprofile.data.remote.network

import com.example.myprofile.common.Constants
import com.example.myprofile.data.remote.model.CourseDTO
import com.example.myprofile.data.remote.model.DetailedTransactionDTO
import com.example.myprofile.data.remote.model.TransactionsDTO
import com.example.myprofile.data.remote.model.WalletDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.TRANSACTIONS_END_POINT)
    suspend fun getTransactions(@Query("page") page: Int): Response<TransactionsDTO>

    @GET(Constants.TRANSACTION_DETAIL_END_POINT)
    suspend fun getTransactionById(@Query("id") id: Int): Response<DetailedTransactionDTO>

    @GET(Constants.WALLETS_END_POINT)
    suspend fun getWallets() : Response<List<WalletDTO>>

    @GET(Constants.COURSES_END_POINT)
    suspend fun getCourse(@Query("from") from: String, @Query("to") to: String) : Response<CourseDTO>
}