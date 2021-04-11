package com.example.tipjar.repository

import androidx.lifecycle.LiveData
import com.example.tipjar.database.dao.TipJarDao
import com.example.tipjar.database.entity.TipHistory
import javax.inject.Inject

class TipJarDataRepository @Inject constructor(
    private val tipJarDao: TipJarDao
) : TipJarRepository {

    private val savedTips = mutableMapOf<String, TipHistory>()

    override suspend fun saveTipCalculation(tipJar: TipHistory) {
        tipJarDao.insert(tipJar)
    }

    override fun loadAllTheSavedTips(): LiveData<List<TipHistory>> {
        return tipJarDao.getAllTheTips()
    }

    override fun loadSaveTip(timeStamp: Long): LiveData<TipHistory> {
        return tipJarDao.getSavedTip(timeStamp)
    }

    override suspend fun deleteTip(tip: TipHistory) {
        tipJarDao.delete(tip)
    }
}
