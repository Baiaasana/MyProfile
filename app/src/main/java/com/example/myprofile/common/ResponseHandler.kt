package com.example.myprofile.common

import retrofit2.Response

class ResponseHandler {

    suspend fun <T> safeApiCall(request: suspend () -> Response<T>): Resource<T> =

        try {
            Resource.loading(null)
            val response = request.invoke()
            val result = response.body()!!
            if (response.isSuccessful) {
                Resource.success(result)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
}