package com.example.tipjar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.Calculator
import com.example.tipjar.testing.OpenForTesting
import kotlinx.coroutines.launch

@OpenForTesting
class HistoryFragmentViewModel @ViewModelInject constructor(private val tipCalculator: Calculator) :
    ViewModel() {

    fun loadTipHistory(): LiveData<List<TipHistory>> {
        return tipCalculator.loadSavedTipCalculations()
    }

    fun deleteItemInList(tipHistory: TipHistory) {
        viewModelScope.launch {
            tipCalculator.deleteTip(tipHistory)
        }
    }
}
