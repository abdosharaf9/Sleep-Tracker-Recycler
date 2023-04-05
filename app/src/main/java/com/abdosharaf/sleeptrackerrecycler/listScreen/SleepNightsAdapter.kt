package com.abdosharaf.sleeptrackerrecycler.listScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdosharaf.sleeptrackerrecycler.database.SleepNight
import com.abdosharaf.sleeptrackerrecycler.databinding.ItemCurrentBinding
import com.abdosharaf.sleeptrackerrecycler.databinding.ItemNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_CURRENT = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SleepNightsAdapter(private val clickListener: (SleepNight) -> Unit) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> SleepNightViewHolder.from(parent)
            ITEM_VIEW_TYPE_CURRENT -> CurrentNightViewHolder.from(parent)
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SleepNightViewHolder -> {
                val item = getItem(position) as DataItem.SleepNightItem
                holder.bind(item.sleepNight, clickListener)
            }
            is CurrentNightViewHolder -> {
                val item = getItem(position) as DataItem.SleepNightCurrent
                holder.bind(item.sleepNight)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.SleepNightCurrent -> ITEM_VIEW_TYPE_CURRENT
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun formatListAndSubmit(nights: MutableList<SleepNight>?) {

        if (nights.isNullOrEmpty()) return

        CoroutineScope(Dispatchers.IO).launch {
            val items = mutableListOf<DataItem>()

            if (nights[0].startTime == nights[0].endTime) {
                items.add(DataItem.SleepNightCurrent(nights[0]))
                nights.removeAt(0)
                items.addAll(nights.map { DataItem.SleepNightItem(it) })
            } else {
                items.addAll(nights.map { DataItem.SleepNightItem(it) })
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}

class SleepNightViewHolder(private val binding: ItemNightBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(night: SleepNight, clickListener: (SleepNight) -> Unit) {
        binding.night = night
        binding.root.setOnClickListener {
            clickListener.invoke(night)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SleepNightViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return SleepNightViewHolder(ItemNightBinding.inflate(layoutInflater, parent, false))
        }
    }
}

class CurrentNightViewHolder(private val binding: ItemCurrentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(night: SleepNight) {
        binding.night = night
    }

    companion object {
        fun from(parent: ViewGroup): CurrentNightViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CurrentNightViewHolder(ItemCurrentBinding.inflate(layoutInflater, parent, false))
        }
    }
}


object SleepNightsDiffUtil : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight) : DataItem() {
        override val id = sleepNight.id
    }

    data class SleepNightCurrent(val sleepNight: SleepNight) : DataItem() {
        override val id = sleepNight.id
    }

    abstract val id: Long
}