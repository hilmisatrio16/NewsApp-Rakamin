package com.rakamin.newsapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakamin.newsapp.R
import com.rakamin.newsapp.adapter.HorizontalListAdapter
import com.rakamin.newsapp.adapter.SearchListAdapter
import com.rakamin.newsapp.adapter.VerticalListAdapter
import com.rakamin.newsapp.data.remote.response.Article
import com.rakamin.newsapp.data.remote.response.Resource
import com.rakamin.newsapp.databinding.FragmentSearchBinding
import com.rakamin.newsapp.ui.home.HomeFragmentDirections
import com.rakamin.newsapp.utils.ConstantValues
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val args: SearchFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ConstantValues) {
            viewModel.getNews(
                ECONOMY_QUERY, COUNTRY, API_KEY, 30, args.mode
            )
        }

        onClick()
        searchNews()
        observerDataNews()
        observerDataSearch()
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchBar.setOnClickListener {
            binding.svNews.show()
        }
    }

    private fun searchNews() {
        binding.svNews.editText.setOnEditorActionListener { textView, _, _ ->
            with(ConstantValues) {
                viewModel.searchNews(textView.text.toString(), API_KEY, PAGE_SIZE)
            }
            true
        }
    }

    private fun observerDataSearch() {
        val searchAdapter = SearchListAdapter {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    it.url
                )
            )
            //dismiss searchview
        }
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
        viewModel.dataSearch.filterNotNull().onEach {
            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    searchAdapter.submitData(it.data?.articles as ArrayList<Article>)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun observerDataNews() {
        val adapterNews = VerticalListAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it.url
                )
            )
        }
        binding.rvAllNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterNews
        }
        viewModel.dataNews.filterNotNull().onEach {
            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    adapterNews.submitData(it.data?.articles as ArrayList<Article>)
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}