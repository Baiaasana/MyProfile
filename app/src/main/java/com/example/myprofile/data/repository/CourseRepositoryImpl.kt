package com.example.myprofile.data.repository

import android.util.Log
import com.example.myprofile.common.Resource
import com.example.myprofile.common.ResponseHandler
import com.example.myprofile.data.remote.model.CourseDTO
import com.example.myprofile.data.remote.network.ApiService
import com.example.myprofile.domain.model.CourseDomain
import com.example.myprofile.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : CourseRepository {
    
    override suspend fun getCourse(from: String, to: String): Flow<Resource<CourseDomain>> {
        return flow {
            val result = responseHandler.safeApiCall { apiService.getCourse(from = from, to = to) }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    val data = result.data!!.toDomain()
                    emit(Resource.success(data))
                    Log.d("log", "logRepo".plus(data))
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