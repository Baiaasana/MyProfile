package com.example.myprofile.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.databinding.TransactionItemBinding
import com.example.myprofile.presenter.model.TransactionsUI

class TransactionAdapter :
    PagingDataAdapter<TransactionsUI.TransactionUI, TransactionAdapter.TransactionViewHolder>(
        TransactionItemCallback) {

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val transaction = getItem(bindingAdapterPosition)!!
            binding.apply {
                tvTitle.text = transaction.title.toString()
                tvSubtitle.text = transaction.subtitle.toString()
                tvAmount.text = transaction.amunt.toString()
                tvCurrency.text = transaction.currency.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) = holder.bind()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionViewHolder(
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    private object TransactionItemCallback : DiffUtil.ItemCallback<TransactionsUI.TransactionUI>() {
        override fun areItemsTheSame(
            oldItem: TransactionsUI.TransactionUI,
            newItem: TransactionsUI.TransactionUI,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionsUI.TransactionUI,
            newItem: TransactionsUI.TransactionUI,
        ): Boolean {
            return oldItem == newItem
        }
    }
}