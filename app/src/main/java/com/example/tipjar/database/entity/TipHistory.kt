package com.example.tipjar.database.entity

import androidx.room.Entity
import androidx.room.TypeConverters

@Entity(primaryKeys = ["timestamp"])
@TypeConverters(DateConverter::class)
data class TipHistory(
    val inputtedAmount: Double = 0.0,
    val inputtedTipInPercent: Double = 0.0,
    val tipPerPerson: Double = 0.0,
    val totalTipAmount: Double = 0.0,
    val grandTotal: Double = 0.0,
    var timestamp: Long = 0,
    var receiptPath: String = "",
    var selectedCurrency: String = ""
)
