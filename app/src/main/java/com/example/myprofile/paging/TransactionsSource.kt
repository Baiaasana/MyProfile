package com.example.myprofile.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myprofile.data.repository.TransactionsRepositoryImpl
import com.example.myprofile.presenter.model.TransactionsUI
import javax.inject.Inject

class TransactionsSource @Inject constructor(private val repositoryImpl: TransactionsRepositoryImpl) :
    PagingSource<Int, TransactionsUI.TransactionUI>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionsUI.TransactionUI>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionsUI.TransactionUI> {
        return try{
            val nextPage = params.key ?: 1
            val response = repositoryImpl.getTransactions(nextPage)

            LoadResult.Page(
                data = response.data!!.items!!.map { it.toPresenter() },
                prevKey = if (nextPage > 1) nextPage - 1 else null,
                nextKey = if (nextPage < 4) nextPage + 1 else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }




}