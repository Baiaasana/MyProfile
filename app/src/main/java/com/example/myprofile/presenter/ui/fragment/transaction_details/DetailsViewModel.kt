package com.example.myprofile.presenter.ui.fragment.transaction_details

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.common.Resource
import com.example.myprofile.domain.use_case.DetailedTransactionUsesCase
import com.example.myprofile.utility.viewStates.DetailedTransactionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val usesCase: DetailedTransactionUsesCase,
) : ViewModel() {

    private val _detailedTransactionFlow =
        MutableStateFlow(DetailedTransactionViewState())
    val detailedTransactionFlow = _detailedTransactionFlow.asStateFlow()

    suspend fun getTransactionById(id: Int) {
        viewModelScope.launch {
            val data = usesCase.invoke(id = id)
            data.collectLatest {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.toPresenter()
                        _detailedTransactionFlow.value =
                            _detailedTransactionFlow.value.copy(isLoading = false, data = result)
                        d("log", "log VM".plus(result))
                        d("log", "log VM".plus(detailedTransactionFlow.value))
                    }
                    Resource.Status.ERROR -> {
                        _detailedTransactionFlow.value = _detailedTransactionFlow.value.copy(
                            isLoading = false,
                            errorMessage = it.message.toString()
                        )
                    }
                    Resource.Status.LOADING -> {
                        _detailedTransactionFlow.value =
                            _detailedTransactionFlow.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}