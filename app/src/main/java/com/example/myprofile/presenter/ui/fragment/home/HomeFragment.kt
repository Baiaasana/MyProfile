package com.example.myprofile.presenter.ui.fragment.home

import android.os.Bundle
import android.view.View
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

    private var scrollPosition = 1

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = binding.rvTransactions.layoutManager as LinearLayoutManager
        scrollPosition = layoutManager.findFirstVisibleItemPosition()
        outState.putInt("scroll_position", scrollPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt("scroll_position", 1)
            binding.rvTransactions.scrollToPosition(scrollPosition)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollPosition = savedInstanceState?.getInt("scroll_position") ?: 1

    }

    override fun onResume() {
        super.onResume()
        if(scrollPosition != -1){
            binding.rvTransactions.scrollToPosition(scrollPosition)
        }
    }

    override fun listeners() {
        transactionAdapter.onTransactionClickListener = {
            navigateToDetails(it)
            onSaveInstanceState(Bundle())
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