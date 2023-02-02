package com.example.myprofile.data.remote.model

import com.example.myprofile.domain.model.TransactionsDomain

data class TransactionsDTO(
    val page: Int?,
    val total_items: Int?,
    val per_page: Int?,
    val items: List<Transaction>?,
    ) {

    fun toDomain(): TransactionsDomain{
        return TransactionsDomain(
            page = page,
            total_items = total_items,
            per_page = per_page,
            items = items,
        )
    }
    data class Transaction(
        val id: Int?,
        val title: String?,
        val subtitle: String?,
        val amunt: Float?,
        val currency: String?,
        val date: Long?,
    ){
        fun toDomain(): TransactionsDomain.TransactionDomain{
            return TransactionsDomain.TransactionDomain(
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
