package com.example.myprofile.presenter.ui.fragment.wallets

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentWalletsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletsFragment : BaseFragment<FragmentWalletsBinding>(FragmentWalletsBinding::inflate) {

    private val viewModel: WalletsViewModel by viewModels()

    override fun listeners() {

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment())
            }
            btnContinue.setOnClickListener {
                findNavController().navigate(WalletsFragmentDirections.actionWalletsFragmentToConvertFragment())
            }
        }
    }

    override fun init() {
    }

    override fun observers() {
    }
}