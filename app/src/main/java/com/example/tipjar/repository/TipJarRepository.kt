package com.example.tipjar.repository

import androidx.lifecycle.LiveData
import com.example.tipjar.database.entity.TipHistory

interface TipJarRepository {
    suspend fun saveTipCalculation(tipJar: TipHistory)
    fun loadAllTheSavedTips(): LiveData<List<TipHistory>>
    fun loadSaveTip(timeStamp: Long): LiveData<TipHistory>
    suspend fun deleteTip(tip: TipHistory)
}
