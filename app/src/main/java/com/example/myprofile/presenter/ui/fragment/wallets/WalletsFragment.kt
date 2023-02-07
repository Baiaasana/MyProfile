package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentWalletsBinding
import com.example.myprofile.presenter.adapter.WalletAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletsFragment : BaseFragment<FragmentWalletsBinding>(FragmentWalletsBinding::inflate) {

    private val viewModel: WalletsViewModel by viewModels()
    private val walletAdapter: WalletAdapter = WalletAdapter()

    override fun listeners() {

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment())
            }
            binding.btnContinue.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment())
            }
        }

        walletAdapter.onWalletClickListener = { wallet ->
            val list = walletAdapter.currentList
            list.forEach {
                it.is_default =false
            }
            walletAdapter.submitList(list.toList())
        }
    }

    override fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getWallets()
        }
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvWallets.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = walletAdapter
        }
    }

    override fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.walletsFlow.collectLatest {
                    walletAdapter.submitList(it.data)
                }
            }
        }
    }
}