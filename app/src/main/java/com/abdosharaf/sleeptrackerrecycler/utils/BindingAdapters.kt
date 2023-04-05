package com.abdosharaf.sleeptrackerrecycler.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.abdosharaf.sleeptrackerrecycler.R
import com.abdosharaf.sleeptrackerrecycler.database.SleepNight

@BindingAdapter("getTimeFormatted")
fun TextView.getTimeFormatted(time: Long) {
    this.text = getTime(time, this.context.resources)
}

@BindingAdapter("getQualityString")
fun TextView.getQualityString(quality: Int) {
    this.text = when(quality) {
        0 -> this.context.getString(R.string.very_poor)
        1 -> this.context.getString(R.string.poor)
        2 -> this.context.getString(R.string.ok)
        3 -> this.context.getString(R.string.good)
        4 -> this.context.getString(R.string.very_good)
        5 -> this.context.getString(R.string.excellent)
        else -> "--"
    }
}

@BindingAdapter("getTotalTime")
fun TextView.getTotalTime(night: SleepNight) {
    this.text = getTotalTime(night.startTime, night.endTime, this.context.resources)
}

@BindingAdapter("setImageQuality")
fun ImageView.setImageQuality(quality: Int) {
    this.setImageResource(
        when(quality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.language_svgrepo_com
        }
    )
}