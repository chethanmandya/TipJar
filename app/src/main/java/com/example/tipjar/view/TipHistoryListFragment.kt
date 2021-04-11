package com.example.tipjar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tipjar.databinding.FragmentHistoryFragmentBinding
import com.example.tipjar.utils.AppExecutors
import com.example.tipjar.viewmodels.HistoryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TipHistoryListFragment : Fragment() {

    private lateinit var binding: FragmentHistoryFragmentBinding
    private val viewModel: HistoryFragmentViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TipHistoryListAdapter(appExecutors) {

            if (it.receiptPath.isNotEmpty())
                ReceiptImageFragment().apply {
                    imagePath = it.receiptPath
                }.show(childFragmentManager, "")
        }
        binding.tipHistoryRecyclerView.adapter = adapter

        viewModel.loadTipHistory().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.toolBarLayout.historyListBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.deleteItemInList(adapter.currentList[viewHolder.adapterPosition])
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.tipHistoryRecyclerView)
    }
}
