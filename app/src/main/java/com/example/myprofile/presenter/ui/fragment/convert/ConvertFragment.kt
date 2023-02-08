package com.example.myprofile.presenter.ui.fragment.convert

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.common.Constants
import com.example.myprofile.common.CourseSymbols
import com.example.myprofile.common.Symbols
import com.example.myprofile.databinding.FragmentConvertBinding
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ConvertFragment : BaseFragment<FragmentConvertBinding>(FragmentConvertBinding::inflate), Symbols {

    private val viewModel: ConvertViewModel by viewModels()
    private val args: ConvertFragmentArgs by navArgs()

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun listeners() {
        binding.imChooseTo.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                type = "to",
            ))
        }

        binding.ivChooseFrom.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                type = "from",
            ))
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

                    updateData(fromID = readID(Constants.KEY_FROM),
                        toID = readID(Constants.KEY_TO),
                        myFrom = binding.etAmountFrom.text.toString().toFloat(),
                        myTo = binding.etAmountTo.text.toString().toFloat(),
                        olfFrom = binding.tvAmountFrom.text.toString().toFloat(),
                        oldTo = binding.tvAmountTo.text.toString().toFloat())

//                    showFromWallet(readID(Constants.KEY_FROM))
//                    showToWallet(readID(Constants.KEY_TO))

//                    updateBalance()

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

    private fun updateData(
        toID: Int,
        fromID: Int,
        olfFrom: Float,
        myFrom: Float,
        oldTo: Float,
        myTo: Float,
    ) {

        updateDataInDatabase(
            walletIdFrom = fromID, walletIdTo = toID,
            newBalanceFrom = olfFrom.minus(myFrom), newBalanceTo = oldTo.plus(myTo)
        )

    }

    private fun readID(key: String): Int {
        var id: Int = 1
        viewLifecycleOwner.lifecycleScope.launch {
            id = viewModel.read(key).toInt()
        }
        return id
    }

    private fun updateBalance() {
        binding.apply {
            tvAmountFrom.text = updateFields(readID(Constants.KEY_FROM)).toString()
            tvAmountTo.text = updateFields(readID(Constants.KEY_TO)).toString()
            etAmountFrom.text?.clear()
        }
    }

    private fun updateFields(walletID: Int): Float {

        var list = mutableListOf<WalletUI>()
        var updatedBalance = 0F

        firebaseDatabase.getReference("wallets").get().addOnSuccessListener {
            it.children.forEach {
                val item = it.getValue(WalletUI::class.java)
                item?.let { it1 -> list.add(it1) }
            }
            updatedBalance = list.find {
                it.id == walletID
            }!!.balance!!

        }.addOnFailureListener {
        }
        return updatedBalance
    }


    private fun updateDataInDatabase(
        walletIdFrom: Int,
        walletIdTo: Int,
        newBalanceFrom: Float,
        newBalanceTo: Float,
    ) {

        var list = mutableListOf<WalletUI>()
        firebaseDatabase.getReference("wallets").get().addOnSuccessListener {
            it.children.forEach {
                val item = it.getValue(WalletUI::class.java)
                item?.let { it1 -> list.add(it1) }
            }

            val updatedList = list.map {
                if (it.id == walletIdFrom) {
                    it.copy(balance = newBalanceFrom)
                } else {
                    it
                }
            }

            val updatedList2 = updatedList.map {
                if (it.id == walletIdTo) {
                    it.copy(balance = newBalanceTo)
                } else {
                    it
                }
            }

            firebaseDatabase.getReference("wallets").removeValue().addOnCompleteListener {
                firebaseDatabase.getReference("wallets").setValue(updatedList2)
            }

        }.addOnFailureListener {
        }
    }

    private fun checkWalletsCourses() =
        binding.tvCurrencyFrom.text.toString() == binding.tvCurrencyTo.text.toString()

    private fun checkFloat(str: String) =
        str[str.length - 3] == '.'

    private fun checkText(text: String) = (text.isNotEmpty() && text.toFloat() > 0)

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uploadWallets()
        }

        args.type.let {
            if (args.type == "from") {
                showFromWallet(readID(Constants.KEY_FROM))
                showToWallet(readID(Constants.KEY_TO))
            }
            if (args.type == "to") {
                showFromWallet(readID(Constants.KEY_FROM))
                showToWallet(readID(Constants.KEY_TO))
            }
        }

//        showSymbols()
//        showCourses()

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

    private fun showFromWallet(fromID: Int) {

        var list = mutableListOf<WalletUI>()
        firebaseDatabase.getReference("wallets").get().addOnSuccessListener {
            it.children.forEach {
                val item = it.getValue(WalletUI::class.java)
                item?.let { it1 -> list.add(it1) }
            }
            val fromItem = list.find { it.id == fromID }!!
            try {
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
                    showCourses()
                    showSymbols()
                }
            } catch (e: Exception) {
                d("log", "log E - ".plus(e.message.toString()))
            }
        }

    }

    private fun showToWallet(toID: Int) {

        var list = mutableListOf<WalletUI>()

        firebaseDatabase.getReference("wallets").get().addOnSuccessListener {
            it.children.forEach {
                val item = it.getValue(WalletUI::class.java)
                item?.let { it1 -> list.add(it1) }
            }
            val toItem = list.find { it.id.toString() == toID.toString() }!!

            try {
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
                    showCourses()
                    showSymbols()
                }
            } catch (e: Exception) {
                d("log", "log E - ".plus(e.message.toString()))
            }
        }
    }

//    private fun setSymbol(course: String): String {
//
//        return when (course) {
//            CourseSymbols.GEL.name -> CourseSymbols.GEL.symbol
//            CourseSymbols.USD.name -> CourseSymbols.USD.symbol
//            CourseSymbols.EUR.name -> CourseSymbols.EUR.symbol
//            else -> CourseSymbols.RUB.symbol
//        }
//    }


    private fun showCourses() {
        when {
            binding.tvCurrencyFrom.text == CourseSymbols.GEL.symbol
                    && binding.tvCurrencyTo.text == CourseSymbols.USD.symbol -> {
                courseFromTo("GEL", "USD")
            }
            binding.tvCurrencyFrom.text == CourseSymbols.USD.symbol
                    && binding.tvCurrencyTo.text == CourseSymbols.GEL.symbol -> {
                courseFromTo("USD", "GEL")
            }
            binding.tvCurrencyFrom.text == CourseSymbols.GEL.symbol
                    && binding.tvCurrencyTo.text == CourseSymbols.EUR.symbol -> {
                courseFromTo("GEL", "EUR")
            }
            binding.tvCurrencyFrom.text == CourseSymbols.GEL.symbol
                    && binding.tvCurrencyTo.text == CourseSymbols.RUB.symbol -> {
                courseFromTo("GEL", "RUB")
            }
            else -> {
                courseFromTo("GEL", "GEL")
            }
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
                binding.apply {
                    tvAmountToNormal.text = (it.data!!.rate ?: "0").toString()
                    tvAmountToOwn.text = (it.data!!.rate ?: "0").toString()
                    tvOne.visibility = View.VISIBLE
                    tvOne2.visibility = View.VISIBLE
                    tvEquals.visibility = View.VISIBLE
                    tvEquals2.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun courseFromTo(from: String, to: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCourse(from, to)
        }
    }

    override fun observers() {
    }

}