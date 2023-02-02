package com.example.myprofile.presenter.ui.fragment.home

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentHomeBinding
import com.example.myprofile.presenter.adapter.TransactionAdapter
import com.example.myprofile.presenter.model.TransactionsUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val transactionAdapter: TransactionAdapter = TransactionAdapter()

    override fun listeners() {
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler(){
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = transactionAdapter
        }
    }

    override fun observers() {
        lifecycleScope.launch {
            viewModel.transactionsPager.collectLatest {
                val data = it
                transactionAdapter.submitData(data)

                var lists : MutableList<TransactionsUI.TransactionUI> = transactionAdapter.snapshot().items.toMutableList()
                d("log", "logF = ".plus(data))
                d("log", "logF 2 = ".plus(lists))

                val groupedMapMap: Map<String, List<TransactionsUI.TransactionUI>> = lists.groupBy {
                    it.date.toString()
                }
            }
        }
    }
}