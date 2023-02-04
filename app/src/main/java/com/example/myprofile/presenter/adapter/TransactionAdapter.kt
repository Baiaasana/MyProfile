package com.example.myprofile.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.databinding.TransactionItemBinding
import com.example.myprofile.presenter.model.TransactionsUI
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter :
    PagingDataAdapter<TransactionsUI.TransactionUI, TransactionAdapter.TransactionViewHolder>(
        TransactionItemCallback) {

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(bindingAdapterPosition) as TransactionsUI.TransactionUI
            if(item.id == 1){
                binding.apply {
                    tvTitle.text = item.title.toString()
                    tvSubtitle.text = item.subtitle.toString()
                    tvAmount.text = item.amunt.toString()
                    tvDatee.visibility = VISIBLE
                    tvDatee.text = item.date.let { getData(it!!.toLong(), "yyyy.MM.dd HH:mm").toString() }
                    tvCurrency.text = item.currency.toString()
                }
            }else{
                val postItem = getItem(bindingAdapterPosition-1) as TransactionsUI.TransactionUI
                if (item.date.toString() == postItem.date.toString()) {
                    binding.apply {
                        tvTitle.text = item.title.toString()
                        tvSubtitle.text = item.subtitle.toString()
                        tvAmount.text = item.amunt.toString()
                        tvDatee.text = ""
                        tvDatee.visibility = GONE
                        tvCurrency.text = item.currency.toString()
                    }
                } else if(item.date.toString() != postItem.date.toString()){
                    binding.apply {
                        tvTitle.text = item.title.toString()
                        tvSubtitle.text = item.subtitle.toString()
                        tvDatee.text = item.date.let { getData(it!!.toLong(), "yyyy.MM.dd HH:mm").toString() }
                        tvAmount.text = item.amunt.toString()
                        tvDatee.visibility = VISIBLE
                        tvCurrency.text = item.currency.toString()
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

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        return holder.bind()
    }
}