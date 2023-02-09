package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.lifecycle.ViewModel
import com.example.myprofile.common.DataStore

class WalletsViewModel : ViewModel() {

    suspend fun save(key: String, value: Int) {
        DataStore.save(key, value)
    }

}