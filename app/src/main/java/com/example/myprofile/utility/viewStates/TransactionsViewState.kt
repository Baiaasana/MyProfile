package com.example.myprofile.utility.viewStates

import com.example.myprofile.presenter.model.TransactionsUI

data class TransactionsViewState(
    val isLoading: Boolean = false,
    val data: TransactionsUI? = null,
    val errorMessage: String = "",
)
