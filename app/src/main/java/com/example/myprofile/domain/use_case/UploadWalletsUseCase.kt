package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Constants
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class UploadWalletsUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
) {

    fun uploadData(walletList: MutableList<WalletUI>) {
        firebaseDatabase.getReference(Constants.PATH).get().addOnSuccessListener {
            if (!it.exists()) {
                firebaseDatabase.getReference(Constants.PATH)
                    .setValue(walletList)
            }
        }.addOnFailureListener {
        }
    }
}

