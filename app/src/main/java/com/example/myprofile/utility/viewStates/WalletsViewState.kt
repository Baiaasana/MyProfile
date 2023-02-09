package com.example.myprofile.utility.viewStates

import com.example.myprofile.presenter.model.WalletUI

data class WalletsViewState(
    val isLoading: Boolean = false,
    val data: List<WalletUI>? = emptyList(),
    val errorMessage: String = "",
)



