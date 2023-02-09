package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.WalletDomain
import com.example.myprofile.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWalletsUseCase @Inject constructor(private val repository: WalletsRepository) {

    suspend fun invoke(): Flow<Resource<List<WalletDomain>>>{
        return repository.getAllWallets()
    }
}