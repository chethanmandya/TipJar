package com.example.tipjar.model

import androidx.lifecycle.LiveData
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.repository.TipJarRepository
import java.math.RoundingMode

interface Calculator {

    fun calculateTip(checkAmount: Double, tipInPercent: Double, numberOfPeople: Int = 0): TipHistory

    suspend fun saveTipCalculation(tipCalculation: TipHistory)

    fun loadSavedTipCalculations(): LiveData<List<TipHistory>>

    suspend fun deleteTip(tipToBeDeleted: TipHistory)
}

class TipCalculator constructor(private val repository: TipJarRepository) :
    Calculator {

    override fun calculateTip(
        checkAmount: Double,
        tipInPercent: Double,
        numberOfPeople: Int
    ): TipHistory {
        val tipAmount = (checkAmount * (tipInPercent / 100.0))
            .toBigDecimal()
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        val grandTotal = checkAmount + tipAmount

        var perPerson = tipAmount
        if (numberOfPeople > 0)
            perPerson = tipAmount / numberOfPeople
        perPerson = perPerson.toBigDecimal()
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        return TipHistory(
            inputtedAmount = checkAmount,
            tipPerPerson = perPerson,
            inputtedTipInPercent = tipInPercent,
            totalTipAmount = tipAmount,
            grandTotal = grandTotal
        )
    }

    override suspend fun saveTipCalculation(tipCalculation: TipHistory) {
        repository.saveTipCalculation(tipCalculation)
    }

    override fun loadSavedTipCalculations(): LiveData<List<TipHistory>> {
        return repository.loadAllTheSavedTips()
    }

    override suspend fun deleteTip(tipToBeDeleted: TipHistory) {
        return repository.deleteTip(tipToBeDeleted)
    }
}
