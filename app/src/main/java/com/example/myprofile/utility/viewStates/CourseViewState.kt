package com.example.myprofile.utility.viewStates

import com.example.myprofile.presenter.model.CourseUI

data class CourseViewState(
    val isLoading: Boolean = false,
    val data: CourseUI? = CourseUI(rate = 1F),
    val errorMessage: String = "",
)