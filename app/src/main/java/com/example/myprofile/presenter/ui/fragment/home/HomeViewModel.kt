package com.example.myprofile.presenter.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myprofile.data.repository.TransactionsRepositoryImpl
import com.example.myprofile.domain.use_case.TransactionsUseCase
import com.example.myprofile.paging.TransactionsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: TransactionsUseCase,
) : ViewModel() {

    val transactionsPager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            TransactionsSource(useCase = useCase)
        }
    ).flow.cachedIn(viewModelScope)
}
