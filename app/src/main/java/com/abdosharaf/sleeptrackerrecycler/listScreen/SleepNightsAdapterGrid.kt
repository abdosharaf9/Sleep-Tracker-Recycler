package com.abdosharaf.sleeptrackerrecycler.listScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdosharaf.sleeptrackerrecycler.database.SleepNight
import com.abdosharaf.sleeptrackerrecycler.databinding.ItemNightGridBinding

class SleepNightsAdapterGrid(private val clickListener: (SleepNight) -> Unit) :
    ListAdapter<SleepNight, SleepNightGridViewHolder>(SleepNightsGridDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightGridViewHolder {
        return SleepNightGridViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightGridViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class SleepNightGridViewHolder(private val binding: ItemNightGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(night: SleepNight, clickListener: (SleepNight) -> Unit) {
        binding.night = night
        binding.root.setOnClickListener {
            clickListener.invoke(night)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SleepNightGridViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return SleepNightGridViewHolder(ItemNightGridBinding.inflate(layoutInflater, parent, false))
        }
    }
}

object SleepNightsGridDiffUtil : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}