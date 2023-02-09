package com.example.myprofile.domain.model

import com.example.myprofile.presenter.model.WalletUI

data class WalletDomain(
    val id: Int?,
    val title: String?,
    val balance: Float?,
    val currency: String?,
    val is_default: Boolean? = false,
    val account_number: Long?,
) {
    fun toPresenter(): WalletUI {
        return WalletUI(
            id = id,
            title = title,
            balance = balance,
            currency = currency,
            is_default = is_default,
            account_number = account_number
        )
    }
}
