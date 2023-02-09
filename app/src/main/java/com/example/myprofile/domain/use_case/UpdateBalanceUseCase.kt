package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Constants
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class UpdateBalanceUseCase @Inject constructor(private val database: FirebaseDatabase) {

    fun updateDataInDatabase(
        walletIdFrom: Int,
        walletIdTo: Int,
        newBalanceFrom: Float,
        newBalanceTo: Float,
    ) {

        var list = mutableListOf<WalletUI>()
        database.getReference(Constants.PATH).get().addOnSuccessListener {
            it.children.forEach {
                val item = it.getValue(WalletUI::class.java)
                item?.let { it1 -> list.add(it1) }
            }

            val updatedList = list.map {
                if (it.id == walletIdFrom) {
                    it.copy(balance = newBalanceFrom)
                } else {
                    it
                }
            }

            val updatedList2 = updatedList.map {
                if (it.id == walletIdTo) {
                    it.copy(balance = newBalanceTo)
                } else {
                    it
                }
            }
            database.getReference(Constants.PATH).removeValue().addOnCompleteListener {
                database.getReference(Constants.PATH).setValue(updatedList2)
            }

        }.addOnFailureListener {
        }
    }
}