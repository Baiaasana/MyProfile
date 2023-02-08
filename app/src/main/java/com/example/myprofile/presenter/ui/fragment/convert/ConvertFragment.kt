package com.example.myprofile.presenter.ui.fragment.convert

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myprofile.common.BaseFragment
import com.example.myprofile.common.CourseSymbols
import com.example.myprofile.databinding.FragmentConvertBinding
import com.example.myprofile.presenter.model.WalletUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ConvertFragment : BaseFragment<FragmentConvertBinding>(FragmentConvertBinding::inflate) {

    private val viewModel: ConvertViewModel by viewModels()
    private val args: ConvertFragmentArgs by navArgs()

    @Inject
    lateinit var database: FirebaseDatabase

    override fun listeners() {
        binding.imChooseTo.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                fromID = args.fromID,
                type = "to",
            ))
        }

        binding.ivChooseFrom.setOnClickListener {
            findNavController().navigate(ConvertFragmentDirections.actionConvertFragmentToWalletsFragment(
                toID = args.toID,
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

                    updateBalance(oldBalance = binding.tvAmountFrom.text.toString().toFloat(),
                        newBalance = binding.tvAmountFrom.text.toString().toFloat()
                            .minus(binding.etAmountFrom.text.toString().toFloat()))

                    updateBalance(oldBalance = binding.tvAmountTo.text.toString().toFloat(),
                        newBalance = binding.tvAccountTo.text.toString().toFloat()
                            .minus(binding.etAmountTo.text.toString().toFloat()))

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

    private fun updateBalance(oldBalance: Float, newBalance: Float) {
        viewModel.updateData(oldBalance = oldBalance, newBalance = newBalance)
    }


    private fun checkWalletsCourses() =
        binding.tvCurrencyFrom.text.toString() == binding.tvCurrencyTo.text.toString()

    private fun checkFloat(str: String) =
        str[str.length - 3] == '.'

    private fun checkText(text: String) = (text.isNotEmpty() && text.toFloat() > 0)

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getWallets()
        }

        if (args.type == "from") {
            showFromWallet(args.fromID)
            showToWallet(args.toID)
        }
        if (args.type == "to") {
            showFromWallet(args.fromID)
            showToWallet(args.toID)
        }

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

    private fun showFromWallet(fromID: Int) {

        var list = mutableListOf<WalletUI>()
        database.getReference("wallets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val item = it.getValue(WalletUI::class.java) ?: return
                        list.add(item)
                    }
                    val fromItem = list.find { it.id.toString() == fromID.toString() }!!
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
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun showToWallet(toID: Int) {

        var list = mutableListOf<WalletUI>()

        database.getReference("wallets")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val item = it.getValue(WalletUI::class.java) ?: return
                        list.add(item)
                    }
                    val toItem = list.find { it.id.toString() == toID.toString() }!!
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
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun setSymbol(course: String): String {

        return when (course) {
            CourseSymbols.GEL.name -> CourseSymbols.GEL.symbol
            CourseSymbols.USD.name -> CourseSymbols.USD.symbol
            CourseSymbols.EUR.name -> CourseSymbols.EUR.symbol
            else -> CourseSymbols.RUB.symbol
        }
    }


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
                binding.tvAmountToNormal.text = it.data!!.rate.toString()
                binding.tvAmountToOwn.text = it.data.rate.toString()
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