package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.CourseDomain
import com.example.myprofile.domain.model.DetailedTransactionDomain
import com.example.myprofile.domain.repository.CourseRepository
import com.example.myprofile.domain.repository.DetailedTransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseUsesCase @Inject constructor(
    private val repository: CourseRepository,
) {
    suspend fun invoke(from: String, to:String): Flow<Resource<CourseDomain>> {
        return repository.getCourse(from, to)
    }
}