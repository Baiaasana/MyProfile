package com.example.myprofile.domain.model

import com.example.myprofile.presenter.model.DetailedTransactionUI

data class DetailedTransactionDomain(
    val id: Int?,
    val title: String?,
    val amount: Float?,
    val currency: String?,
    val sender: UserDomain?,
    val receiver: UserDomain?,
    val documentNumber: Int?,
    val operationCode: String?,
    val soldAmount: String?,
    val purchasedAmount: String?,
    val course: String?,
) {
    fun toPresenter(): DetailedTransactionUI {
        return DetailedTransactionUI(
            id = id,
            title = title,
            amount = amount,
            currency = currency,
            sender = sender!!.toPresenter(),
            receiver = receiver!!.toPresenter(),
            documentNumber = documentNumber,
            operationCode = operationCode,
            soldAmount = soldAmount,
            purchasedAmount = purchasedAmount,
            course = course
        )
    }

    data class UserDomain(
        val name: String?,
        val accountNumber: String?,
        val bankName: String?,
        val bankCode: String?,
    ) {
        fun toPresenter(): DetailedTransactionUI.UserUI {
            return DetailedTransactionUI.UserUI(
                name = name,
                accountNumber = accountNumber,
                bankName = bankName,
                bankCode = bankCode
            )
        }

    }
}
