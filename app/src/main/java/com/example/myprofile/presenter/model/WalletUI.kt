package com.example.myprofile.presenter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletUI(
    val id: Int? = 0,
    val title: String? = "",
    val balance: Float? = 0F,
    val currency: String? = "",
    var is_default: Boolean? = false,
    val account_number: Long? = 0L,
) : Parcelable

