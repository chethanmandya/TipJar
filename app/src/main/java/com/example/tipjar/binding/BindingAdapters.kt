package com.example.tipjar.binding

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.tipjar.utils.DateUtils
import com.example.tipjar.utils.ImageUtils

@BindingAdapter("setTimeStamp")
fun AppCompatTextView.setTimeStamp(value: Long) {
    this.text = DateUtils.getDate(value, "EEE, MMM d, yyyy h:mm a")
}

@BindingAdapter("receiptImage")
fun AppCompatImageView.setReceiptImage(imagePath: String) {
    val bitmap = ImageUtils.readBitmap(imagePath)
    this.setImageBitmap(bitmap)
}

// Textview , double
@BindingAdapter("android:text")
fun setText(view: TextView, value: Double) {
    var setValue = view.text?.isEmpty() ?: false
    try {
        if (setValue.not()) {
            setValue = view.text.toString().toDouble() != value
        }
    } catch (e: NumberFormatException) {
    }
    if (setValue) {
        view.text = value.toString()
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: TextView): Double {
    return try {
        view.text.toString().toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}

// Textview , Int
@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    var setValue = view.text?.isEmpty() ?: false
    try {
        if (setValue.not()) {
            setValue = view.text.toString().toInt() != value
        }
    } catch (e: NumberFormatException) {
    }
    if (setValue) {
        view.text = value.toString()
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getTipText(view: TextView): Int {
    return try {
        view.text.toString().toInt()
    } catch (e: NumberFormatException) {
        0
    }
}
