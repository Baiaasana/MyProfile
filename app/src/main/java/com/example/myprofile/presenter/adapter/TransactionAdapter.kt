package com.example.myprofile.presenter.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.Config
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.common.Constants
import com.example.myprofile.common.Utility
import com.example.myprofile.databinding.TransactionItemBinding
import com.example.myprofile.presenter.model.TransactionsUI

class TransactionAdapter :
    PagingDataAdapter<TransactionsUI.TransactionUI, TransactionAdapter.TransactionViewHolder>(
        TransactionItemCallback), Utility {

    var onTransactionClickListener: ((TransactionsUI.TransactionUI) -> Unit?)? = null

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(bindingAdapterPosition) as TransactionsUI.TransactionUI
            if (item.id == 1) {
                binding.apply {
                    tvTitle.text = item.title.toString()
                    tvSubtitle.text = item.subtitle.toString()
                    tvAmount.text = item.amunt.toString()
                    tvDate.visibility = VISIBLE
                    tvDate.text =
                        item.date.let { getData(it!!.toLong(), Constants.DATA_FORMAT).toString() }
                    tvCurrency.text = item.currency.toString()
                    root.setOnClickListener {
                        onTransactionClickListener?.invoke(item)
                    }
                }
            }else{
                val postItem = getItem(bindingAdapterPosition-1) as TransactionsUI.TransactionUI
                if (item.date.toString() == postItem.date.toString()) {
                    binding.apply {
                        tvTitle.text = item.title.toString()
                        tvSubtitle.text = item.subtitle.toString()
                        tvAmount.text = item.amunt.toString()
                        tvDate.text = ""
                        tvDate.visibility = GONE
                        tvCurrency.text = item.currency.toString()
                        root.setOnClickListener {
                            onTransactionClickListener?.invoke(item)
                        }
                    }
                } else if(item.date.toString() != postItem.date.toString()){
                    binding.apply {
                        tvTitle.text = item.title.toString()
                        tvSubtitle.text = item.subtitle.toString()
                        tvDate.text =
                            item.date.let { getData(it!!.toLong(), Constants.DATA_FORMAT).toString() }
                        tvAmount.text = item.amunt.toString()
                        tvDate.visibility = VISIBLE
                        tvCurrency.text = item.currency.toString()
                        root.setOnClickListener {
                            onTransactionClickListener?.invoke(item)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TransactionAdapter.TransactionViewHolder {
        return TransactionViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    private object TransactionItemCallback :
        DiffUtil.ItemCallback<TransactionsUI.TransactionUI>() {
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

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        return holder.bind()
    }
}
