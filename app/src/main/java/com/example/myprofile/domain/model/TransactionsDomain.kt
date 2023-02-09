package com.example.myprofile.domain.model

import com.example.myprofile.presenter.model.TransactionsUI

data class TransactionsDomain(
    val page: Int?,
    val total_items: Int?,
    val per_page: Int?,
    val items: List<TransactionDomain>?,
) {
    fun toPresenter(): TransactionsUI{
        return TransactionsUI(
            page = page,
            total_items = total_items,
            per_page = per_page,
            items = items!!.map { it.toPresenter() }
        )
    }
    data class TransactionDomain(
        val id: Int?,
        val title: String?,
        val subtitle: String?,
        val amunt: Float?,
        val currency: String?,
        val date: Long?,
    ){
        fun toPresenter(): TransactionsUI.TransactionUI{
            return TransactionsUI.TransactionUI(
                id = id,
                title = title,
                subtitle = subtitle,
                amunt = amunt,
                currency = currency,
                date = date
            )
        }
    }
}

