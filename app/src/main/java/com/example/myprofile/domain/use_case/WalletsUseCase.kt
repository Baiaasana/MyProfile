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

}

