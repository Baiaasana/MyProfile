package com.example.myprofile.presenter.model

data class DetailedTransactionUI(
    val id: Int?,
    val title: String?,
    val amount: Float?,
    val currency: String?,
    val sender: UserUI?,
    val receiver: UserUI?,
    val documentNumber: Int?,
    val operationCode: String?,
    val soldAmount: String?,
    val purchasedAmount: String?,
    val course: String?,
) {
    data class UserUI(
        val name: String?,
        val accountNumber: String?,
        val bankName: String?,
        val bankCode: String?,
    )
}
