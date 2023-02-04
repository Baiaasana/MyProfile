package com.example.myprofile.data.repository

import android.util.Log.d
import com.example.myprofile.common.Resource
import com.example.myprofile.common.ResponseHandler
import com.example.myprofile.data.remote.network.ApiService
import com.example.myprofile.domain.model.DetailedTransactionDomain
import com.example.myprofile.domain.repository.DetailedTransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailedTransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : DetailedTransactionRepository {

    override suspend fun getTransactionById(id: Int): Flow<Resource<DetailedTransactionDomain>> =
        flow {
            val result = responseHandler.safeApiCall { apiService.getTransactionById(id = id) }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    val data = result.data!!.toDomain()
                    emit(Resource.success(data))
                    d("log", "logRepo".plus(data))
                }
                Resource.Status.ERROR -> {
                    emit(Resource.error(result.message.toString()))
                }
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
            }
        }
}
