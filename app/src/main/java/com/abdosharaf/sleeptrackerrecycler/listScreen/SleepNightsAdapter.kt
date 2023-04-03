package com.abdosharaf.sleeptrackerrecycler.listScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdosharaf.sleeptrackerrecycler.database.SleepNight
import com.abdosharaf.sleeptrackerrecycler.databinding.ItemNightBinding

class SleepNightsAdapter(private val clickListener: (SleepNight) -> Unit) : ListAdapter<SleepNight, SleepNightViewHolder>(SleepNightsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightViewHolder {
        return SleepNightViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SleepNightViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class SleepNightViewHolder(private val binding: ItemNightBinding) : RecyclerView.ViewHolder(binding.root) {

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

object SleepNightsDiffUtil : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
}