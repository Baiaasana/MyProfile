package com.example.myprofile.utility.viewStates

import com.example.myprofile.presenter.model.DetailedTransactionUI

data class DetailedTransactionViewState(
    val isLoading: Boolean = false,
    val data: DetailedTransactionUI? = null,
    val errorMessage: String = "",
)
