package com.abdosharaf.sleeptrackerrecycler.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase

class DetailsViewModelFactory(private val database: SleepNightsDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(database) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}