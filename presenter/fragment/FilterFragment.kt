package com.evgeny5454.testapp.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny5454.testapp.databinding.FragmentFilterBinding
import com.evgeny5454.testapp.other.Constants.filterList
import com.evgeny5454.testapp.presenter.adapter.FilterAdapter
import com.evgeny5454.testapp.presenter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : Fragment() {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentFilterBinding

    @Inject
    lateinit var adapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        initRecyclerView()
        initObservers()
        initUpdateAdapter()
        setButtonListener()
        return binding.root
    }

    private fun setButtonListener() {
        binding.checkButton.setOnClickListener {
            viewModel.setFilter(adapter.currentList)
            findNavController().popBackStack()
        }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initUpdateAdapter() {
        adapter.setOnItemClickListener { position ->
            val newList = filterList.toMutableList()
            newList[position] = newList[position].copy(isChecked = true)
            adapter.submitList(newList)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        viewModel.filter.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}