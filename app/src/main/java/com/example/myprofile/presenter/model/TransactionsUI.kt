package com.example.myprofile.presenter.model

import com.example.myprofile.data.remote.model.TransactionsDTO

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


sealed class UIModel{
    data class ItemsUI(
        val page: Int?,
        val total_items: Int?,
        val per_page: Int?,
        val items: List<TransactionsDTO.Transaction>?,
    ) {
        data class ItemUI(
            val id: Int?,
            val title: String?,
            val subtitle: String?,
            val amunt: Float?,
            val currency: String?,
            val date: Long?,
        ) : UIModel() {
            constructor(item: ItemUI) : this(item.id, item.title, item.subtitle, item.amunt, item.currency, item.date)
        }
    }
    data class SeparatorModel(val date: Long?): UIModel(){
        constructor(item: TransactionsUI.TransactionUI) : this(item.date)
    }
}

