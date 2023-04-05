package com.abdosharaf.sleeptrackerrecycler.detailsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdosharaf.sleeptrackerrecycler.database.SleepNight
import com.abdosharaf.sleeptrackerrecycler.database.SleepNightsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val database: SleepNightsDatabase): ViewModel() {

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    fun doneNavigation() {
        _navigateBack.value = false
    }

    private suspend fun deleteNight(night: SleepNight) {
        database.sleepNightsDAO.deleteNight(night)
    }

    fun delete(night: SleepNight) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNight(night)
        }
        _navigateBack.value = true
    }
}