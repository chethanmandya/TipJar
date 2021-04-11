package com.example.tipjar.viewmodel

import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.Calculator
import com.example.tipjar.viewmodels.HomeScreenViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeScreenViewModelTest {

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @Mock
    private lateinit var mockCalculator: Calculator

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        homeScreenViewModel = HomeScreenViewModel(mockCalculator)
    }

    @Test
    fun testCalculateTip() {
        homeScreenViewModel.userInputValuesOnUi.enteredAmount = "10.00"
        homeScreenViewModel.userInputValuesOnUi.enteredTipInPercentage = "15"

        val stub = TipHistory(
            inputtedAmount = 10.00,
            inputtedTipInPercent = 15.0,
            totalTipAmount = 1.5,
            tipPerPerson = 0.0,
            grandTotal = 11.5
        )
        `when`(mockCalculator.calculateTip(10.00, 15.0, 0)).thenReturn(stub)

        homeScreenViewModel.calculateTipWithUserInput(homeScreenViewModel.userInputValuesOnUi)

        assertEquals(homeScreenViewModel.resultedValuesToUi.grandTotal, 11.5)
        assertEquals(homeScreenViewModel.resultedValuesToUi.perPerson, 0.0)
        assertEquals(homeScreenViewModel.resultedValuesToUi.totalTip, 1.5)
    }

    @Test
    fun testCalculateTipBadTipPercent() {
        homeScreenViewModel.userInputValuesOnUi.enteredAmount = "10.00"
        homeScreenViewModel.userInputValuesOnUi.enteredTipInPercentage = "" // Bad tip percent

        homeScreenViewModel.calculateTipWithUserInput(homeScreenViewModel.userInputValuesOnUi)

        Mockito.verify(mockCalculator, Mockito.never())
            .calculateTip(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt())
    }

    @Test
    fun testCalculateTipBadCheckAmount() {

        homeScreenViewModel.userInputValuesOnUi.enteredAmount = ""
        homeScreenViewModel.userInputValuesOnUi.enteredTipInPercentage = "15" // Bad tip percent

        homeScreenViewModel.calculateTipWithUserInput(homeScreenViewModel.userInputValuesOnUi)

        Mockito.verify(mockCalculator, Mockito.never())
            .calculateTip(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSaveCurrentTip() {
        val stub = TipHistory(
            inputtedAmount = 10.00,
            inputtedTipInPercent = 15.0,
            totalTipAmount = 1.5,
            tipPerPerson = 0.0,
            grandTotal = 11.5
        )
        val capturedImagePath = "savedUrl"

        fun setupTipCalculation() {
            homeScreenViewModel.userInputValuesOnUi.enteredAmount = "10.00"
            homeScreenViewModel.userInputValuesOnUi.enteredTipInPercentage = "15"

            `when`(mockCalculator.calculateTip(10.00, 15.0, 0)).thenReturn(stub)
        }

        setupTipCalculation()
        homeScreenViewModel.calculateTipWithUserInput(homeScreenViewModel.userInputValuesOnUi)
        homeScreenViewModel.onClickingSaveButton(capturedImagePath)

        // After saving we are clearing inputted elements , check entered amount is empty
        assertEquals(homeScreenViewModel.userInputValuesOnUi.enteredAmount, "")
    }
}
