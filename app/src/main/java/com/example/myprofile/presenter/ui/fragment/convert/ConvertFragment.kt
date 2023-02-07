package com.example.myprofile.presenter.ui.fragment.convert

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.common.CourseSymbols
import com.example.myprofile.databinding.FragmentConvertBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertFragment : BaseFragment<FragmentConvertBinding>(FragmentConvertBinding::inflate) {

    private val viewModel: ConvertViewModel by viewModels()

    override fun listeners() {
        binding.imChooseTo.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment())
        }

        binding.ivChooseFrom.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment())
        }

        binding.etAmountFrom.doOnTextChanged { text, start, count, after ->
            try {
                if (checkText(text.toString())) {
                    convertGELtoUSD(text.toString().toFloat())
                } else {
                    convertGELtoUSD(0.00F)
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(context, "შეიყვანეთ რიცხვი", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnContinue.setOnClickListener {
            try {
                if (checkWalletsCourses()) {
                    Toast.makeText(context, "აირჩიეთ ვალიდური ანგარიში", Toast.LENGTH_SHORT).show()
                } else if (checkText(binding.etAmountFrom.text.toString()) && checkFloat(binding.etAmountFrom.text.toString())) {
                    Toast.makeText(context, "ოპერაცია წარმატებით შესრულდა", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context,
                        "შეიყვანეთ რიცხვი 1.00 ფორმატში",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

                Toast.makeText(context,
                    "შეიყვანეთ რიცხვი 1.00 ფორმატში",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkWalletsCourses() =
        binding.tvCurrencyFrom.text.toString() == binding.tvCurrencyTo.text.toString()

    private fun checkFloat(str: String) =
        str[str.length - 3] == '.'

    private fun checkText(text: String) = (text.isNotEmpty() && text.toFloat() > 0)

    override fun init() {
        showSymbols()
        showCourses()
    }

    private fun showSymbols() {
        binding.apply {
            val fromSymbol = tvCurrencyFrom.text.toString()
            tveCurrencyFrom.text = fromSymbol
            tvCurrencyFromNormal.text = fromSymbol
            tvCurrencyFromOwn.text = fromSymbol
            val toSymbol = tvCurrencyTo.text.toString()
            tveCurrencyTo.text = toSymbol
            tvCurrencyToNormal.text = toSymbol
            tvCurrencyToOwn.text = toSymbol
        }
    }

    private fun showCourses() {
        if (binding.tvCurrencyFrom.text == CourseSymbols.GEL.symbol
            && binding.tvCurrencyTo.text == CourseSymbols.USD.symbol
        ) {
            courseFromGELtoUSD()
        } else if (binding.tvCurrencyFrom.text == CourseSymbols.USD.symbol
            && binding.tvCurrencyTo.text == CourseSymbols.GEL.symbol
        ) {
            courseFromUSDtoGEL()
        }
        getCourses()

    }

    private fun convertGELtoUSD(amount: Float? = 0.00F) {
        binding.etAmountTo.text =
            (amount?.times(binding.tvAmountToNormal.text.toString().toFloat())).toString()
    }

    private fun getCourses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courseFlow.collectLatest {
                binding.tvAmountToNormal.text = it.data!!.rate.toString()
                binding.tvAmountToOwn.text = it.data.rate.toString()
            }
        }
    }

    private fun courseFromGELtoUSD() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCourse("GEL", "USD")
        }
    }

    private fun courseFromUSDtoGEL() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCourse("USD", "GEL")
        }
    }

    override fun observers() {
    }

}