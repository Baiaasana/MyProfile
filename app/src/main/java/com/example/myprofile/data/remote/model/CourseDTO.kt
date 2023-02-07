package com.example.myprofile.data.remote.model

import com.example.myprofile.domain.model.CourseDomain

data class CourseDTO(
    val rate: Float?
){
    fun toDomain(): CourseDomain{
        return CourseDomain(
            rate = rate
        )
    }
}
