package com.example.wbinternw7part3.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wbinternw7part3.databinding.FragmentFavoriteBinding
import com.example.wbinternw7part3.model.AppState
import com.example.wbinternw7part3.view.favorite.adapter.FavoriteAdapter
import com.example.wbinternw7part3.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initView()
    }

    private fun initView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoriteRecyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getData()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessFavorite -> {
                if (appState.favoriteData.isNotEmpty())
                    adapter.setData(appState.favoriteData)
                else
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}