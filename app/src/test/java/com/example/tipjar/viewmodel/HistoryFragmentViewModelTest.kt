package com.example.tipjar.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
        val result = mock<Observer<List<TipHistory>>>()
        historyFragmentViewModel.loadTipHistory().observeForever(result)

        // check it call - repository.loadAllTheSavedTips()
        Mockito.verify(repository).loadAllTheSavedTips()
    }
}
