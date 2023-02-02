package com.example.myprofile.presenter.ui.fragment.transaction_details

import androidx.fragment.app.viewModels
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
    }

    override fun observers() {
    }
}