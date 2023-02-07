package com.example.myprofile.presenter.ui.fragment.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.common.Resource
import com.example.myprofile.data.repository.CourseRepositoryImpl
import com.example.myprofile.domain.use_case.CourseUsesCase
import com.example.myprofile.utility.viewStates.CourseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(private val usesCase: CourseUsesCase) :
    ViewModel() {

    private val _courseFlow = MutableStateFlow(CourseViewState())
    val courseFlow = _courseFlow.asStateFlow()

    suspend fun getCourse(from: String, to: String) {
        viewModelScope.launch {
            val data = usesCase.invoke(from, to)
            data.collectLatest {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val data = it.data!!.toPresenter()
                        _courseFlow.value = _courseFlow.value.copy(data = data)
                    }
                    Resource.Status.ERROR -> {
                        _courseFlow.value = _courseFlow.value.copy(
                            isLoading = false,
                            errorMessage = it.message.toString()
                        )
                    }
                    Resource.Status.LOADING -> {
                        _courseFlow.value =
                            _courseFlow.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}