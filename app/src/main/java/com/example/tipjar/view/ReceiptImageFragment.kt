package com.example.tipjar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tipjar.databinding.DialogFragmentReceiptBinding

class ReceiptImageFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentReceiptBinding
    var imagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagePath = imagePath
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }
}
