package com.example.tipjar.viewmodel

import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.TipCalculator
import com.example.tipjar.repository.TipJarRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class TipCalculatorTest {

    lateinit var calculator: TipCalculator
    private val repository = Mockito.mock(TipJarRepository::class.java)

    @Before
    fun setup() {
        calculator = TipCalculator(repository)
    }

    @Test
    fun testCalculateTip() {
        val baseTipCalculation = TipHistory(inputtedAmount = 10.0)

        val testValues = listOf(
            baseTipCalculation.copy(
                inputtedTipInPercent = 25.0,
                totalTipAmount = 2.5,
                grandTotal = 12.50, tipPerPerson = 2.5
            ),
            baseTipCalculation.copy(
                inputtedTipInPercent = 15.0,
                totalTipAmount = 1.5,
                grandTotal = 11.50, tipPerPerson = 1.5
            ),
            baseTipCalculation.copy(
                inputtedTipInPercent = 18.0,
                totalTipAmount = 1.8,
                grandTotal = 11.80, tipPerPerson = 1.8
            )
        )

        testValues.forEach {
            assertEquals(
                it,
                calculator.calculateTip(it.inputtedAmount, it.inputtedTipInPercent)
            )
        }
    }
}
