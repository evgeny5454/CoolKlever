package com.evgeny5454.testapp.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evgeny5454.testapp.R
import com.evgeny5454.testapp.databinding.ItemFilterButtonBinding
import com.evgeny5454.testapp.presenter.model.Filter
import javax.inject.Inject

class FilterAdapter @Inject constructor(): ListAdapter<Filter, FilterAdapter.ItemHolder>(DiffUtilCallback) {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(position: (Int) -> Unit) {
        onItemClickListener = position
    }

    inner class ItemHolder(private val binding: ItemFilterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context
        fun bind(filter: Filter) = with(binding) {
            button.text = filter.filterName
            if (filter.isChecked) {
                button.iconTint =  getColorStateList(context, R.color.green_check)
                button.strokeColor = getColorStateList(context, R.color.green_check)
            } else {
                button.iconTint =  getColorStateList(context, R.color.green_not_check)
                button.strokeColor = getColorStateList(context, R.color.white)
            }
            button.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(
                        filter.id
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemFilterButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<Filter>() {
        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Filter, newItem: Filter): Any? {
            return if (oldItem.isChecked != newItem.isChecked) true else null
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}