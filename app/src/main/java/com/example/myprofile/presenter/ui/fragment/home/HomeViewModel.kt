package com.example.myprofile.presenter.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myprofile.data.repository.TransactionsRepositoryImpl
import com.example.myprofile.paging.TransactionsSource
import com.example.myprofile.presenter.model.TransactionsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryImpl: TransactionsRepositoryImpl,
) : ViewModel() {

    val transactionsPager : Flow<PagingData<TransactionsUI.TransactionUI>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            TransactionsSource(repositoryImpl = repositoryImpl)
        }
    ).flow.cachedIn(viewModelScope)
}
