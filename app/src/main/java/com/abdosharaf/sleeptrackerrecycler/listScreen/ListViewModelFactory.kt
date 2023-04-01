package com.abdosharaf.sleeptrackerrecycler.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase

class ListViewModelFactory(private val database: SleepNightsDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(database) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}