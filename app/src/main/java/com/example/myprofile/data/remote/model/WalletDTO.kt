package com.example.myprofile.data.remote.model

import com.example.myprofile.domain.model.WalletDomain

data class WalletDTO(
    val id: Int?,
    val title: String?,
    val balance: Float?,
    val currency: String?,
    val is_default: Boolean? = false,
    val account_number: Long?
){
    fun toDomain(): WalletDomain{
        return WalletDomain(
            id = id,
            title = title,
            balance = balance,
            currency = currency,
            is_default = is_default,
            account_number = account_number
        )
    }

}
