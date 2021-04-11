package com.example.tipjar.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.tipjar.database.dao.TipJarDao
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.utils.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class TipJarDataRepositoryTest {

    private lateinit var repository: TipJarRepository

    @Mock
    private lateinit var tipJarDao: TipJarDao

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = TipJarDataRepository(tipJarDao)
    }

    @Test
    fun loadTipBasedOnTimeStamp() {
        val tipCalculation = TipHistory(
            inputtedAmount = 10.00,
            inputtedTipInPercent = 15.0,
            totalTipAmount = 1.5,
            tipPerPerson = 0.0,
            grandTotal = 11.5
        )

        tipCalculation.timestamp = System.currentTimeMillis()

        val dbResult = MutableLiveData<TipHistory>()
        val observer = mock<Observer<TipHistory>>()

        // mocking method to return table result
        Mockito.`when`(tipJarDao.getSavedTip(tipCalculation.timestamp)).thenReturn(dbResult)
        // setting up observer to loadSavedTip
        repository.loadSaveTip(tipCalculation.timestamp).observeForever(observer)
        // posting tip data
        dbResult.postValue(tipCalculation)
        // Finally , Check received object is same posted value
        Mockito.verify(observer).onChanged(tipCalculation)
    }

    @Test
    fun loadListOfSavedTips() {

        val baseTipCalculation = TipHistory(inputtedAmount = 10.0)

        val testValues = listOf(
            baseTipCalculation.copy(
                inputtedTipInPercent = 25.0,
                totalTipAmount = 2.5,
                grandTotal = 12.50, tipPerPerson = 2.5, timestamp = System.currentTimeMillis()
            ),
            baseTipCalculation.copy(
                inputtedTipInPercent = 15.0,
                totalTipAmount = 1.5,
                grandTotal = 11.50, tipPerPerson = 1.5, timestamp = System.currentTimeMillis()
            ),
            baseTipCalculation.copy(
                inputtedTipInPercent = 18.0,
                totalTipAmount = 1.8,
                grandTotal = 11.80, tipPerPerson = 1.8, timestamp = System.currentTimeMillis()
            )
        )

        val dbResult = MutableLiveData<List<TipHistory>>()
        val observer = mock<Observer<List<TipHistory>>>()

        // mocking method to return table result
        Mockito.`when`(tipJarDao.getAllTheTips()).thenReturn(dbResult)
        // setting up observer to loadSavedTip
        repository.loadAllTheSavedTips().observeForever(observer)
        // posting tip data
        dbResult.postValue(testValues)
        // Finally , Check received object is same posted value
        Mockito.verify(observer).onChanged(testValues)
    }
}
