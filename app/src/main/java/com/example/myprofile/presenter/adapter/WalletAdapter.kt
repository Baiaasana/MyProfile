package com.example.myprofile.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.common.CourseSymbols
import com.example.myprofile.databinding.WalletItemBinding
import com.example.myprofile.presenter.model.WalletUI

class WalletAdapter :
    ListAdapter<WalletUI, WalletAdapter.WalletViewHolder>(ItemCallback) {

    var onWalletClickListener: ((WalletUI) -> Unit)? = null

    inner class WalletViewHolder(private val binding: WalletItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = getItem(bindingAdapterPosition)
            binding.apply {
                tvTitle.text = item.title.toString()
                tvAccountNumber.text = item.account_number.toString().plus("(${item.currency})")
                tvAmount.text = item.balance.toString()
                if (item.is_default == true) {
                    ivIsDefault.visibility = View.VISIBLE
                }
                if (item.is_default == false) {
                    ivIsDefault.visibility = View.GONE
                }
                tvCurrency.text = setSymbol(item.currency.toString())

                itemView.setOnClickListener {
                    onWalletClickListener?.invoke(item)
                    currentList.forEach {
                        it.is_default = false
                    }
                    item.is_default = true
                    ivIsDefault.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setSymbol(course: String): String {

        return when (course) {
            CourseSymbols.GEL.name -> CourseSymbols.GEL.symbol
            CourseSymbols.USD.name -> CourseSymbols.USD.symbol
            CourseSymbols.EUR.name -> CourseSymbols.EUR.symbol
            else -> CourseSymbols.RUB.symbol
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder =
        WalletViewHolder(WalletItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) = holder.bind()

    object ItemCallback : DiffUtil.ItemCallback<WalletUI>() {
        override fun areItemsTheSame(
            oldItem: WalletUI,
            newItem: WalletUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WalletUI,
            newItem: WalletUI,
        ): Boolean {
            return oldItem == newItem
        }
    }
}