package com.example.tipjar.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tipjar.R
import com.example.tipjar.databinding.FragmentHomeBinding
import com.example.tipjar.utils.ImageDetails
import com.example.tipjar.utils.ImageUtils
import com.example.tipjar.utils.presentUserMessage
import com.example.tipjar.viewmodels.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    private val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                requireContext().presentUserMessage(getString(R.string.permission_granted))
                saveTipWithReceiptImage()
            } else {
                requireContext().presentUserMessage(getString(R.string.permission_denied))
            }
        }

    private lateinit var savedImageDetails: ImageDetails
    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                saveTip(savedImageDetails.imageAbsolutePath)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = homeScreenViewModel

        binding.saveButton.setOnClickListener {
            when {
                homeScreenViewModel.tipToBeSave == null -> {
                    requireContext().presentUserMessage(getString(R.string.enter_your_amount))
                }
                binding.saveReceiptCheckBox.isChecked -> saveTipWithReceiptImage()
                else -> saveTip()
            }
        }

        binding.currencySelectionTextView.setOnClickListener { v: View ->
            showMenu(v, R.menu.popup_menu)
        }

        binding.toolBarLayout.tipHistoryIcon.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.fragment_home)
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeToHistory())
        }
    }

    private fun saveTipWithReceiptImage() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasCameraPermission.not())
            requestSinglePermissionLauncher.launch(Manifest.permission.CAMERA)
        else {
            savedImageDetails = ImageUtils.createImageFile(requireContext())
            takePicture.launch(savedImageDetails.uri)
        }
    }

    private fun saveTip(capturedImageFilePath: String = "") {
        homeScreenViewModel.onClickingSaveButton(capturedImageFilePath)
            .observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(HomeScreenFragmentDirections.actionHomeToHistory())
                }
            }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }

        popup.setOnMenuItemClickListener { item: MenuItem ->

            item.title?.split("-")?.getOrNull(0)?.let { currencySymbol ->
                binding.currencySelectionTextView.text = currencySymbol
            }

            true
        }
        // Show the popup menu.
        popup.show()
    }
}
