package com.abdosharaf.sleeptrackerrecycler.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.format.DateUtils
import com.abdosharaf.sleeptrackerrecycler.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
/**
 * Take a date in millis and format it, then return is as a string to use it as a
 * value for any TextView
 * @param date The date you need to format in millis
 * @return String value of the formatted date in this form "EEEE dd-MMM-yyyy 'at' hh:mm a".
 * */
fun getTime(date: Long, res: Resources): String {
    return SimpleDateFormat("EEEE dd-MMM-yyyy '${res.getString(R.string.at)}' hh:mm a").format(date).toString()
}

/**
 * Take 2 dates in millis and get the time between them as a string
 * to use it as a value for any TextView.
 * @param startTime The first date in millis
 * @param endTime The second date in millis
 * @return String value of how much time between these two dates in this form: {d} Days {h} Hours
 * {m} Minutes {s} Seconds.
 * */
fun getTotalTime(startTime: Long, endTime: Long, res: Resources): String {

    var difference = endTime - startTime

    val days = difference / DateUtils.DAY_IN_MILLIS
    difference -= days * DateUtils.DAY_IN_MILLIS

    val hours = difference / DateUtils.HOUR_IN_MILLIS
    difference -= hours * DateUtils.HOUR_IN_MILLIS

    val minutes = difference / DateUtils.MINUTE_IN_MILLIS
    difference -= minutes * DateUtils.MINUTE_IN_MILLIS

    val seconds = difference / 1000


    var total = ""

    if (days > 0) {
        total += "$days ${res.getString(R.string.days)} "
    }
    if (hours > 0) {
        total += "$hours ${res.getString(R.string.hours)} "
    }
    if (minutes > 0) {
        total += "$minutes ${res.getString(R.string.minutes)} "
    }

    total += "${seconds+1} ${res.getString(R.string.seconds)}"

    return total
}