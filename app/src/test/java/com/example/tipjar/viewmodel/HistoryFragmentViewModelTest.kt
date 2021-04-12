package com.example.tipjar.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.tipjar.database.dao.TipJarDao
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.Calculator
import com.example.tipjar.repository.TipJarRepository
import com.example.tipjar.utils.mock
import com.example.tipjar.viewmodels.HistoryFragmentViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HistoryFragmentViewModelTest {

    private lateinit var historyFragmentViewModel: HistoryFragmentViewModel

    @Mock
    private lateinit var mockCalculator: Calculator

    @Mock
    private lateinit var tipJarDao: TipJarDao

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TipJarRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        historyFragmentViewModel = HistoryFragmentViewModel(mockCalculator)
    }

    @Test
    fun basic() {
        val observer = mock<Observer<List<TipHistory>>>()
        val dbResult = MutableLiveData<List<TipHistory>>()

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

        // mocking method to return table result
        Mockito.`when`(historyFragmentViewModel.loadTipHistory()).thenReturn(dbResult)

        historyFragmentViewModel.loadTipHistory().observeForever(observer)

        // posting tip data
        dbResult.postValue(testValues)

        // Finally , Check received object is same posted value
        Mockito.verify(observer).onChanged(testValues)
    }
}
