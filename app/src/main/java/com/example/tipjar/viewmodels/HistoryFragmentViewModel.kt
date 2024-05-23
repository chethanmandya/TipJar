package com.example.tipjar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.Calculator
import com.example.tipjar.testing.OpenForTesting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(private val tipCalculator: Calculator) :
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
