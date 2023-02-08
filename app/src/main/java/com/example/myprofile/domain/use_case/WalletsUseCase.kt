package com.example.myprofile.domain.use_case

import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class WalletsUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
) {

    fun uploadData(walletList: MutableList<WalletUI>) {

        firebaseDatabase.getReference("wallets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        firebaseDatabase.getReference("wallets")
                            .setValue(walletList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun updateData(oldBalance: Float, newBalance: Float) {

        var list = mutableListOf<WalletUI>()

        firebaseDatabase.getReference("wallets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val item = it.getValue(WalletUI::class.java) ?: return
                        list.add(item)
                    }
                    list.find {
                        it.balance.toString().toFloat() == oldBalance
                    }!!.balance!!.plus(newBalance-oldBalance)
                    firebaseDatabase.getReference("wallets").setValue(list)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}

