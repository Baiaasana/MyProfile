package com.example.myprofile.presenter.ui.fragment.transaction_details

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }
    }

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTransactionById(id = args.id)
        }
    }

    override fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailedTransactionFlow.collectLatest {
                val item = it.data
                binding.apply {
                    tvAmount.text = "-".plus(item?.amount.toString())
                    tvCourse.text = item?.course.toString()
                    tvDate.text = args.date.let { getData(it, "yyyy.MM.dd").toString() }
                    tvCurrency.text = item?.currency.toString()
                    tvSoldAmount.text = item?.soldAmount.toString()
                    tvPurchasedAmount.text = item?.purchasedAmount.toString()
                    tvDocumentNumber.text = item?.documentNumber.toString()
                    tvOperationCode.text = item?.operationCode.toString()
                    tvReceiver.text = item?.receiver?.name.toString()
                    tvReceiverBank.text = item?.receiver?.bankName.toString()
                    tvReceiverBankCode.text = item?.receiver?.bankCode.toString()
                    tvReceiverNumber.text = item?.receiver?.accountNumber.toString()
                    tvSender.text = item?.sender?.name.toString()
                    tvSenderBank.text = item?.sender?.bankName.toString()
                    tvSenderBankCode.text = item?.sender?.bankCode.toString()
                    tvSenderNumber.text = item?.sender?.accountNumber.toString()
                    tvTransactionAmount.text =
                        item?.amount.toString().plus(item?.currency.toString())
                    tvTitle.text = item?.title.toString()
                }
                d("log", "log F ".plus(item))
            }
        }
    }

    private fun getData(milliSeconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}