package com.example.myprofile.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.common.Constants
import com.example.myprofile.databinding.DateItemBinding
import com.example.myprofile.databinding.TransactionItemBinding
import com.example.myprofile.presenter.model.TransactionsUI
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter :
    PagingDataAdapter<TransactionsUI.TransactionUI, RecyclerView.ViewHolder>(
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

    inner class DateViewHolder(private val binding: DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val transaction = getItem(bindingAdapterPosition)
            binding.tvDate.text = transaction!!.date.let { getData(it!!.toLong(), "yyyy.MM.dd HH:mm").toString() }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> {
                holder.bind()
            }
            is DateViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.TRANSACTION)
            TransactionViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        else
            DateViewHolder(
                DateItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemViewType(position: Int): Int {
        if(getItem(position)!!.id.toString().toInt() > 1){
            if(getItem(position)!!.date == getItem(position -1)!!.date){
                return Constants.TRANSACTION
            }else return Constants.DATE
        }else return Constants.DATE
    }

    fun getData(milliSeconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
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
}