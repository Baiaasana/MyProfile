package com.example.myprofile.presenter.model

data class TransactionsUI(
    val page: Int?,
    val total_items: Int?,
    val per_page: Int?,
    val items: List<TransactionUI>,
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

