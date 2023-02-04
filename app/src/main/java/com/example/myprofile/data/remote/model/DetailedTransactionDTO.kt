package com.example.myprofile.data.remote.model

import com.example.myprofile.domain.model.DetailedTransactionDomain

data class DetailedTransactionDTO(
    val id: Int?,
    val title: String?,
    val amount: Float?,
    val currency: String?,
    val sender: UserDTO? = UserDTO(),
    val receiver: UserDTO? = UserDTO(),
    val documentNumber: Int? = 1234,
    val operationCode: String? = "ISS",
    val soldAmount: String? = "123$",
    val purchasedAmount: String? = "246₾",
    val course: String? = "1 USD - 2.8897 GEL",
) {
    fun toDomain(): DetailedTransactionDomain {
        return DetailedTransactionDomain(
            id = id,
            title = title,
            amount = amount,
            currency = currency,
            sender = sender!!.toDomain(),
            receiver = receiver!!.toDomain(),
            documentNumber = documentNumber,
            operationCode = operationCode,
            soldAmount = soldAmount,
            purchasedAmount = purchasedAmount,
            course = course
        )
    }

    data class UserDTO(
        val name: String? = "გადარიცხვის სატრანზიტო ანგარიში - Ucc",
        val accountNumber: String? = "GE54LB0113150534591010",
        val bankName: String? = "სს თიბისი ბანკი",
        val bankCode: String? = "TBCBGE22",
    ) {
        fun toDomain(): DetailedTransactionDomain.UserDomain {
            return DetailedTransactionDomain.UserDomain(
                name = name,
                accountNumber = accountNumber,
                bankName = bankName,
                bankCode = bankCode
            )
        }
    }
}
