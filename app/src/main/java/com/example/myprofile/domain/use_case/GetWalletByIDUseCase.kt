package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Constants
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWalletByIDUseCase @Inject constructor(private val database: FirebaseDatabase) {

    suspend fun getWalletById(id: Int): WalletUI {
        return withContext(Dispatchers.IO) {
            var list = mutableListOf<WalletUI>()
            var walletItem: WalletUI = WalletUI()

            database.getReference(Constants.PATH).get().await().children
                .forEach {
                    val item = it.getValue(WalletUI::class.java)
                    item?.let { it1 -> list.add(it1) }
                }
            walletItem = list.find { it.id == id }!!
            walletItem
        }
    }
}