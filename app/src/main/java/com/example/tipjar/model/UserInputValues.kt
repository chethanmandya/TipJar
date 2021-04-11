package com.example.tipjar.model

import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.tipjar.BR

/**
 * Data class that captures user entered values
 */
@Keep
class UserInputValues : BaseObservable() {

    @Bindable
    var enteredAmount: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.enteredAmount)
        }

    @Bindable
    var numberOfPeople: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberOfPeople)
        }

    @Bindable
    var enteredTipInPercentage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.enteredTipInPercentage)
        }

    @Bindable
    var selectedCurrency: String = "$"
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedCurrency)
        }
}
