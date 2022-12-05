package com.evgeny5454.testapp.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny5454.testapp.R
import com.evgeny5454.testapp.databinding.FragmentMainBinding
import com.evgeny5454.testapp.other.Status
import com.evgeny5454.testapp.presenter.adapter.CharacterAdapter
import com.evgeny5454.testapp.presenter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding
    private var isLoading = false

    @Inject
    lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        initObservers()
        initRecyclerView()
        initFilterButton()
        initNavigationToDetaleScreen()
        return binding.root
    }

    private fun initNavigationToDetaleScreen() {
        adapter.setOnItemClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initFilterButton() {
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        viewModel.loadState.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    loadingShow()
                }
                Status.ERROR -> {
                    loadingHide()
                    if (!isLoading) {
                        viewModel.getDataFromDatabase()
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                    isLoading = true
                }
                Status.SUCCESS -> {
                    loadingHide()
                    if (!isLoading) viewModel.getDataFromDatabase()
                    isLoading = true
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
        viewModel.data.observe(viewLifecycleOwner) {

            adapter.submitList(it)
        }
    }

    private fun loadingShow() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun loadingHide() {
        binding.progressCircular.visibility = View.GONE
    }
}