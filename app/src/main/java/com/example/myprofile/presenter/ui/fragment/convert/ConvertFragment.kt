package com.example.myprofile.presenter.ui.fragment.convert

import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myprofile.R
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.common.Constants
import com.example.myprofile.common.Utility
import com.example.myprofile.databinding.FragmentConvertBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertFragment : BaseFragment<FragmentConvertBinding>(FragmentConvertBinding::inflate),
    Utility {

    private val viewModel: ConvertViewModel by viewModels()
    private val args: ConvertFragmentArgs by navArgs()

    override fun listeners() {
        binding.imChooseTo.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                type = Constants.TO,
            ))
        }

        binding.ivChooseFrom.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                type = Constants.FROM,
            ))
        }

        binding.etAmountFrom.doOnTextChanged { text, start, count, after ->
            try {
                if (text.toString().toFloat() > binding.tvAmountFrom.text.toString().toFloat() ){
                    Toast.makeText(context, getString(R.string.enter_valid_number), Toast.LENGTH_SHORT).show()
                    binding.btnContinue.isClickable = false
                }else if (checkText(text.toString())) {
                    binding.etAmountTo.text =
                        viewModel.convertToAnotherCourse(text.toString().toFloat(),
                            rate = binding.tvAmountToNormal.text.toString().toFloat()).toString()
                } else {
                    binding.etAmountTo.text = viewModel.convertToAnotherCourse(0.00F,
                        rate = binding.tvAmountToNormal.text.toString().toFloat()).toString()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(context, getString(R.string.enter_digits), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnContinue.setOnClickListener {
            try {
                if (coursesAreEquals(binding.tvCurrencyFrom.text.toString(),
                        binding.tvCurrencyTo.text.toString())
                ) {
                    Toast.makeText(context, getString(R.string.choose_valid_wallet), Toast.LENGTH_SHORT).show()
                } else if (binding.etAmountTo.text == Constants.ZERO) {
                    Toast.makeText(context,
                        getString(R.string.service_error),
                        Toast.LENGTH_SHORT).show()
                    clearFields()
                } else if (checkText(binding.etAmountFrom.text.toString()) && checkFloat(binding.etAmountFrom.text.toString())) {
                    Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT)
                        .show()

                    updateData(fromID = readID(Constants.FROM),
                        toID = readID(Constants.TO),
                        myFrom = binding.etAmountFrom.text.toString().toFloat(),
                        myTo = binding.etAmountTo.text.toString().toFloat(),
                        olfFrom = binding.tvAmountFrom.text.toString().toFloat(),
                        oldTo = binding.tvAmountTo.text.toString().toFloat())
                        .also { clearFields() }

                } else {
                    Toast.makeText(context, getString(R.string.format_error), Toast.LENGTH_SHORT)
                        .show()
                    binding.btnContinue.isClickable = false
                    clearFields()
                }
            } catch (e: Exception) {
                Toast.makeText(context,  getString(R.string.format_error), Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }
    }

    private fun updateData(
        toID: Int,
        fromID: Int,
        olfFrom: Float,
        myFrom: Float,
        oldTo: Float,
        myTo: Float,
    ) {
        viewModel.updateDataInDatabase(walletIdFrom = fromID,
            walletIdTo = toID,
            newBalanceFrom = olfFrom.minus(myFrom),
            newBalanceTo = oldTo.plus(myTo))
        binding.tvAmountFrom.text = olfFrom.minus(myFrom).toString()
        binding.tvAmountTo.text = oldTo.plus(myTo).toString()
    }

    private fun readID(key: String): Int {
        var id = 1
        viewLifecycleOwner.lifecycleScope.launch {
            id = viewModel.read(key).toInt()
        }
        return id
    }

    private fun clearFields() {
        binding.etAmountFrom.text?.clear()
        binding.etAmountTo.text = ""
    }

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uploadWallets()
        }

        args.type.let {
            if (args.type == Constants.FROM) {
                showFROMINFO(readID(Constants.FROM))
                showTOINFO(readID(Constants.TO))
            }
            if (args.type == Constants.TO) {
                showFROMINFO(readID(Constants.FROM))
                showTOINFO(readID(Constants.TO))
            }
        }
    }

    private fun showFROMINFO(fromID: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val resultFrom = async { viewModel.getWalletById(fromID) }
                val fromItem = resultFrom.await()
                binding.apply {
                    tvCurrencyFrom.text = setSymbol(fromItem.currency.toString())
                    val toSymbol = tvCurrencyFrom.text.toString()
                    tveCurrencyFrom.text = toSymbol
                    tvCurrencyFromNormal.text = toSymbol
                    tvCurrencyFromOwn.text = toSymbol
                    tvTitleFrom.text = fromItem.title.toString()
                    tvAccountFrom.text = fromItem.account_number.toString()
                        .plus("(${fromItem.currency.toString()})")
                    tvAmountFrom.text = fromItem.balance.toString()
                    viewModel.showCourses(binding.tvCurrencyFrom.text.toString(),
                        binding.tvCurrencyTo.text.toString())
                    getCourses()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTOINFO(toID: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val resultTo = async { viewModel.getWalletById(toID) }
                val toItem = resultTo.await()
                binding.apply {
                    tvCurrencyTo.text = setSymbol(toItem.currency.toString())
                    val toSymbol = tvCurrencyTo.text.toString()
                    tveCurrencyTo.text = toSymbol
                    tvCurrencyToNormal.text = toSymbol
                    tvCurrencyToOwn.text = toSymbol
                    tvTitleTo.text = toItem.title.toString()
                    tvAccountTo.text = toItem.account_number.toString()
                        .plus("(${toItem.currency.toString()})")
                    tvAmountTo.text = toItem.balance.toString()
                    viewModel.showCourses(binding.tvCurrencyFrom.text.toString(),
                        binding.tvCurrencyTo.text.toString())
                    getCourses()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCourses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courseFlow.collectLatest {
                binding.apply {
                    tvAmountToNormal.text = (it.data!!.rate ?: "0").toString()
                    tvAmountToOwn.text = (it.data.rate ?: "0").toString()
                    tvOne.visibility = View.VISIBLE
                    tvOne2.visibility = View.VISIBLE
                    tvEquals.visibility = View.VISIBLE
                    tvEquals2.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun observers() {
    }
}