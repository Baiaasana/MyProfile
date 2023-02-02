package com.example.myprofile.presenter.model

import com.example.myprofile.data.remote.model.TransactionsDTO
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class TransactionsUI(
    val page: Int?,
    val total_items: Int?,
    val per_page: Int?,
    val items: List<TransactionsDTO.Transaction>?,
) {
    data class TransactionUI(
        val id: Int?,
        val title: String?,
        val subtitle: String?,
        val amunt: Float?,
        val currency: String?,
        val date: Long?,
    )
}
