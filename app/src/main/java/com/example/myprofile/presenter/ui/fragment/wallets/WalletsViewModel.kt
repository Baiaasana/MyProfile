package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.lifecycle.ViewModel
import com.example.myprofile.common.DataStore
import com.example.myprofile.domain.use_case.GetWalletsFromDBUseCase
import com.example.myprofile.presenter.model.WalletUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WalletsViewModel @Inject constructor(private val getWalletsFromDBUseCase: GetWalletsFromDBUseCase): ViewModel() {

    suspend fun save(key: String, value: Int) {
        DataStore.save(key, value)
    }

    suspend fun getWallets(): List<WalletUI> {
        return withContext(Dispatchers.IO) {
            getWalletsFromDBUseCase.getWallets()
        }
    }
}