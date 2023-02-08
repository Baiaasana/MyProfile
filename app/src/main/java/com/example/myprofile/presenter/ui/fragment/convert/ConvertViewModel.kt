package com.example.myprofile.presenter.ui.fragment.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.common.DataStore
import com.example.myprofile.common.Resource
import com.example.myprofile.domain.use_case.GetCourseUsesCase
import com.example.myprofile.domain.use_case.WalletsUseCase
import com.example.myprofile.domain.use_case.GetWalletsUsesCase
import com.example.myprofile.utility.viewStates.CourseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val getCourseUsesCase: GetCourseUsesCase,
    private val getWalletsUsesCase: GetWalletsUsesCase,
    private val walletsUseCase: WalletsUseCase
) :
    ViewModel() {

    private val _courseFlow = MutableStateFlow(CourseViewState())
    val courseFlow = _courseFlow.asStateFlow()

    suspend fun getCourse(from: String, to: String) {
        viewModelScope.launch {
            val data = getCourseUsesCase.invoke(from, to)
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


    suspend fun uploadWallets() {
        viewModelScope.launch {
            val data = getWalletsUsesCase.invoke()
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val listOfWalletsUI = it.data!!.map {
                            it.toPresenter()
                        }.toMutableList()
                        walletsUseCase.uploadData(listOfWalletsUI)
                    }
                    Resource.Status.ERROR -> {

                    }
                    Resource.Status.LOADING -> {

                    }
                }
            }
        }
    }

     fun updateData(walletID:Float, newBalance: Float){
        walletsUseCase.updateData(oldBalance = walletID, newBalance = newBalance)
    }

    suspend fun read(key: String) : String {
        return DataStore.read(key)!!
    }

}