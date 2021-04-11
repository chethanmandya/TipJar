package com.example.tipjar.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.tipjar.common.DataBoundListAdapter
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.databinding.AdapterHistoryItemBinding
import com.example.tipjar.utils.AppExecutors

class TipHistoryListAdapter(
    appExecutors: AppExecutors,
    private val clickCallback: ((TipHistory) -> Unit)
) :
    DataBoundListAdapter<TipHistory, AdapterHistoryItemBinding>(
        appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<TipHistory>() {
            override fun areItemsTheSame(
                oldItem: TipHistory,
                newItem: TipHistory
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TipHistory,
                newItem: TipHistory
            ) = oldItem == newItem
        }
    ) {

    override fun bind(binding: AdapterHistoryItemBinding, item: TipHistory) {
        binding.item = item
        binding.root.setOnClickListener {
            clickCallback(item)
        }
    }

    override fun createBinding(parent: ViewGroup): AdapterHistoryItemBinding =
        AdapterHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
}
