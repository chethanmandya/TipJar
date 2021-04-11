package com.example.tipjar.model

import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.tipjar.BR

/**
 * Data class that captures user entered values
 */
@Keep
class ResultedValues : BaseObservable() {

    @Bindable
    var totalTip: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalTip)
        }

    @Bindable
    var perPerson: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.perPerson)
        }

    @Bindable
    var grandTotal: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.grandTotal)
        }
}
