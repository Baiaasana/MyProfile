package com.example.myprofile.presenter.ui.fragment.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.common.CourseSymbols
import com.example.myprofile.common.DataStore
import com.example.myprofile.common.Resource
import com.example.myprofile.domain.use_case.*
import com.example.myprofile.presenter.model.WalletUI
import com.example.myprofile.utility.viewStates.CourseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    private val getWalletsUseCase: GetWalletsUseCase,
    private val uploadWalletsUseCase: UploadWalletsUseCase,
    private val updateBalanceUseCase: UpdateBalanceUseCase,
    private val getWalletByIDUseCase: GetWalletByIDUseCase,
) :
    ViewModel() {

    private val _courseFlow = MutableStateFlow(CourseViewState())
    val courseFlow = _courseFlow.asStateFlow()

    suspend fun getCourse(from: String, to: String) {
        viewModelScope.launch {
            val data = getCourseUseCase.invoke(from, to)
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
            val data = getWalletsUseCase.invoke()
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val listOfWalletsUI = it.data!!.map {
                            it.toPresenter()
                        }.toMutableList()
                        uploadWalletsUseCase.uploadData(listOfWalletsUI)
                    }
                    Resource.Status.ERROR -> {

                    }
                    Resource.Status.LOADING -> {
                    }
                }
            }
        }
    }

    suspend fun getWalletById(id: Int): WalletUI {
        return withContext(Dispatchers.IO) {
            getWalletByIDUseCase.getWalletById(id = id)
        }
    }

    fun updateDataInDatabase(
        walletIdFrom: Int,
        walletIdTo: Int,
        newBalanceFrom: Float,
        newBalanceTo: Float,
    ) =
        updateBalanceUseCase.updateDataInDatabase(walletIdFrom = walletIdFrom,
            walletIdTo = walletIdTo,
            newBalanceFrom = newBalanceFrom,
            newBalanceTo = newBalanceTo)

    suspend fun read(key: String): String {
        return DataStore.read(key)!!
    }

    fun convertToAnotherCourse(amount: Float? = 0.0F, rate: Float): Float? {
        return amount?.times(rate)
    }

    fun showCourses(fromCourse: String, toCourse: String) {
        viewModelScope.launch {
            when {
                fromCourse == CourseSymbols.GEL.symbol
                        && toCourse == CourseSymbols.USD.symbol -> {
                    getCourse(CourseSymbols.GEL.name, CourseSymbols.USD.name)
                }
                fromCourse == CourseSymbols.USD.symbol
                        && toCourse == CourseSymbols.GEL.symbol -> {
                    getCourse(CourseSymbols.USD.name, CourseSymbols.GEL.name)
                }
                fromCourse == CourseSymbols.GEL.symbol
                        && toCourse == CourseSymbols.EUR.symbol -> {
                    getCourse(CourseSymbols.GEL.name, CourseSymbols.EUR.name)
                }
                fromCourse == CourseSymbols.GEL.symbol
                        && toCourse == CourseSymbols.RUB.symbol -> {
                    getCourse(CourseSymbols.GEL.name, CourseSymbols.RUB.name)
                }
                else -> {
                    getCourse(CourseSymbols.GEL.name, CourseSymbols.GEL.name)
                }
            }
        }
    }
}