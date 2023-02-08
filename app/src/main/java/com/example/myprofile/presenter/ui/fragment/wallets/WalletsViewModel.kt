package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.common.Resource
import com.example.myprofile.domain.use_case.GetWalletsUsesCase
import com.example.myprofile.domain.use_case.WalletsUseCase
import com.example.myprofile.presenter.model.WalletUI
import com.example.myprofile.utility.viewStates.WalletsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletsViewModel @Inject constructor(
    private val usesCase: GetWalletsUsesCase,
    private val walletsUsesCase: WalletsUseCase,
) : ViewModel() {

    private val _walletsFlow = MutableStateFlow(WalletsViewState())
    val walletsFlow = _walletsFlow.asStateFlow()

//    fun getWallets(): List<WalletUI> {
//        return walletsUsesCase.showData()
//    }

//    suspend fun getWallets() {
//        viewModelScope.launch {
//            val data = usesCase.invoke()
//            data.collect {
//                when (it.status) {
//                    Resource.Status.SUCCESS -> {
//                        val result = it.data!!.map {
//                            it.toPresenter()
//                        }.toList()
//                        _walletsFlow.value =
//                            _walletsFlow.value.copy(isLoading = false, data = result)
//                    }
//                    Resource.Status.ERROR -> {
//                        _walletsFlow.value = _walletsFlow.value.copy(isLoading = false,
//                            errorMessage = it.message.toString())
//                    }
//                    Resource.Status.LOADING -> {
//                        _walletsFlow.value =
//                            _walletsFlow.value.copy(isLoading = true, data = emptyList())
//                    }
//                }
//            }
//        }
//    }

}