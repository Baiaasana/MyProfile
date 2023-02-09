package com.example.myprofile.data.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.common.ResponseHandler
import com.example.myprofile.data.remote.network.ApiService
import com.example.myprofile.domain.model.WalletDomain
import com.example.myprofile.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : WalletsRepository {

    override suspend fun getAllWallets(): Flow<Resource<List<WalletDomain>>> {
        return flow {
            val result = responseHandler.safeApiCall { apiService.getWallets() }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    emit(Resource.success(result.data!!.map {
                        it.toDomain()
                    }.toList()))
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
}