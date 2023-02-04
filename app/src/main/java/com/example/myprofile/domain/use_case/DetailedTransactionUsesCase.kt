package com.example.myprofile.domain.use_case

import com.example.myprofile.common.Resource
import com.example.myprofile.domain.model.DetailedTransactionDomain
import com.example.myprofile.domain.repository.DetailedTransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailedTransactionUsesCase @Inject constructor(
    private val repository: DetailedTransactionRepository,
) {
    suspend fun invoke(id: Int): Flow<Resource<DetailedTransactionDomain>> {
        return repository.getTransactionById(id = id)
    }
}