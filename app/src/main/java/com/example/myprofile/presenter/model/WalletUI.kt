package com.example.myprofile.presenter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletUI(
    val id: Int?,
    val title: String?,
    val balance: Float?,
    val currency: String?,
    var is_default: Boolean? = false,
    val account_number: Long?
): Parcelable
