package com.example.myprofile.presenter.ui.fragment.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        transactionAdapter.onTransactionClickListener = {
            navigateToDetails(it)
        }
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = transactionAdapter
        }
    }

    private fun navigateToDetails(transaction: TransactionsUI.TransactionUI) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                id = transaction.id!!.toInt(), date = transaction.date!!
            )
        )
    }

    override fun observers() {
        lifecycleScope.launch {
            viewModel.transactionsPager.collectLatest {
                transactionAdapter.submitData(it)
            }
        }
    }
}