package com.example.myprofile.presenter.ui.fragment.convert

import androidx.fragment.app.viewModels
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentConvertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConvertFragment : BaseFragment<FragmentConvertBinding>(FragmentConvertBinding::inflate) {

    private val viewModel: ConvertViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
    }

    override fun observers() {
    }

}