package com.example.myprofile.presenter.ui.fragment.wallets

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.common.Constants
import com.example.myprofile.databinding.FragmentWalletsBinding
import com.example.myprofile.presenter.adapter.WalletAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletsFragment : BaseFragment<FragmentWalletsBinding>(FragmentWalletsBinding::inflate) {

    private val viewModel: WalletsViewModel by viewModels()
    private val walletAdapter: WalletAdapter = WalletAdapter()
    private val args: WalletsFragmentArgs by navArgs()

    override fun listeners() {

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment(type = args.type))
            }
            binding.btnContinue.setOnClickListener {

                if (args.type == Constants.FROM) {
                    findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment(
                        type = args.type))
                }
                if (args.type == Constants.TO) {
                    findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment(
                        type = args.type))
                }
            }
        }

        walletAdapter.onWalletClickListener = { wallet ->

            if (args.type == Constants.FROM) {
                saveID(Constants.FROM, wallet.id!!.toInt())
            }
            if (args.type == Constants.TO) {
                saveID(Constants.TO, wallet.id!!.toInt())
            }
        }
    }

    private fun saveID(key: String, value: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.save(key, value)
            }
        }
    }

    override fun init() {
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
            try {
                val resultList = async { viewModel.getWallets() }
                val list = resultList.await()
                walletAdapter.submitList(list)
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}