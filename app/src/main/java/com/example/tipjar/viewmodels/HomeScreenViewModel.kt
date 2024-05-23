package com.example.tipjar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.model.Calculator
import com.example.tipjar.model.ResultedValues
import com.example.tipjar.model.UserInputValues
import com.example.tipjar.testing.OpenForTesting
import com.example.tipjar.utils.addOnPropertyChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val tipCalculator: Calculator) :
    ViewModel() {

    val userInputValuesOnUi = UserInputValues()
    val resultedValuesToUi = ResultedValues()
    var tipToBeSave: TipHistory? = null

    init {
        userInputValuesOnUi.addOnPropertyChanged {
            calculateTipWithUserInput(it)
        }
    }

    fun calculateTipWithUserInput(userInput: UserInputValues) {
        var calculatedValues: TipHistory? = null

        if (userInput.enteredAmount.isNotEmpty() && userInput.enteredTipInPercentage.isNotEmpty())
            calculatedValues = tipCalculator.calculateTip(
                userInput.enteredAmount.toDouble(),
                userInput.enteredTipInPercentage.toDouble(),
                userInput.numberOfPeople
            )

        calculatedValues?.let {
            resultedValuesToUi.apply {
                totalTip = calculatedValues.totalTipAmount
                perPerson = calculatedValues.tipPerPerson
                grandTotal = calculatedValues.grandTotal
            }
        }

        tipToBeSave = calculatedValues
    }

    fun onPressingIncrement() {
        var presentValue = userInputValuesOnUi.numberOfPeople
        presentValue++
        userInputValuesOnUi.numberOfPeople = presentValue
    }

    fun onPressingDecrement() {
        var presentValue = userInputValuesOnUi.numberOfPeople
        presentValue = if (presentValue == 0) presentValue else --presentValue
        userInputValuesOnUi.numberOfPeople = presentValue
    }

    fun onClickingSaveButton(receiptPath: String): LiveData<Boolean> {
        // why do we need livedata here? saving data in db is a asynchronous operation,
        // we need to navigate to next screen only after saving data, we should wait for it
        val onSaving = MutableLiveData<Boolean>(false)
        // lunch will execute things sequential , so that it make sure save task finish first then to move to navigate
        viewModelScope.launch {
            tipToBeSave?.let {
                it.receiptPath = receiptPath
                it.selectedCurrency = userInputValuesOnUi.selectedCurrency
                it.timestamp = System.currentTimeMillis()
                tipCalculator.saveTipCalculation(it)
            }

            clearUserInput()
            onSaving.value = true
        }
        return onSaving
    }

    private fun clearUserInput() {
        userInputValuesOnUi.apply {
            enteredAmount = ""
            enteredTipInPercentage = ""
            numberOfPeople = 0
        }

        resultedValuesToUi.apply {
            totalTip = 0.0
            perPerson = 0.0
            grandTotal = 0.0
        }

        tipToBeSave = null
    }
}
