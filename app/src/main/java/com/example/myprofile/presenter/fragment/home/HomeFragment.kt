package com.example.myprofile.presenter.fragment.home

import androidx.fragment.app.viewModels
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
    }

    override fun observers() {
    }
}