package com.example.myprofile.presenter.fragment.wallets

import androidx.fragment.app.viewModels
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentWalletsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletsFragment : BaseFragment<FragmentWalletsBinding>(FragmentWalletsBinding::inflate) {

    private val viewModel: WalletsViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
    }

    override fun observers() {
    }
}