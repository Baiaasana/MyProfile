package com.example.myprofile.domain.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.data.remote.model.CourseDTO
import com.example.myprofile.domain.model.CourseDomain
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getCourse(from: String, to: String): Flow<Resource<CourseDomain>>

}