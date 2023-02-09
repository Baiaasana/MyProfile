package com.example.myprofile.domain.model

import com.example.myprofile.presenter.model.CourseUI

data class CourseDomain(
    val rate: Float?
){
    fun toPresenter(): CourseUI{
        return CourseUI(
            rate = rate
        )
    }
}
