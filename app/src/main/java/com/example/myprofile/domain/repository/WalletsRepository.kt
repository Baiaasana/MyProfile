package com.example.myprofile.domain.repository

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.WalletDomain
import kotlinx.coroutines.flow.Flow

interface WalletsRepository {

    suspend fun getAllWallets() : Flow<Resource<List<WalletDomain>>>

}