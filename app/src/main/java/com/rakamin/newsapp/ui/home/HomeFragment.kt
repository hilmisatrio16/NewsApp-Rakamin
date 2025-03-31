package com.rakamin.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rakamin.newsapp.R
import com.rakamin.newsapp.adapter.HorizontalListAdapter
import com.rakamin.newsapp.adapter.SearchListAdapter
import com.rakamin.newsapp.adapter.VerticalListAdapter
import com.rakamin.newsapp.data.remote.response.Resource
import com.rakamin.newsapp.databinding.FragmentHomeBinding
import com.rakamin.newsapp.model.DataArticle
import com.rakamin.newsapp.utils.ConstantValues
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(ConstantValues) {
            viewModel.getAllNews(ECONOMY_QUERY, API_KEY, PAGE_SIZE)
            viewModel.getHeadlines(COUNTRY, API_KEY)
        }



        onClick()
        searchNews()
        observerDataAllNews()
        observerDataHeadlineNews()
        observerDataSearch()
    }

    private fun observerDataSearch() {
        val searchAdapter = SearchListAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it.url
                )
            )
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
                    val articleList = it.data?.articles?.map { data ->
                        DataArticle(
                            data.author?: "Author",
                            data.publishedAt,
                            data.source.name,
                            data.title,
                            data.url,
                            data.urlToImage
                        )
                    }
                    searchAdapter.submitData(articleList as ArrayList<DataArticle>)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun searchNews() {
        binding.svNews.editText.setOnEditorActionListener { textView, _, _ ->
            with(ConstantValues) {
                viewModel.searchNews(textView.text.toString(), API_KEY, PAGE_SIZE)
            }
            true
        }
    }

    private fun onClick() {
        binding.tvMoreHeadline.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                    ConstantValues.HEADLINE
                )
            )
        }

        binding.tvMoreNews.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(ConstantValues.ALLNEWS)
            )
        }

        binding.btnSearch.setOnClickListener {
            binding.svNews.show()
        }
    }

    private fun observerDataHeadlineNews() {
        val adapterHeadlineNews = HorizontalListAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it.url
                )
            )
        }
        binding.rvTopHeadline.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterHeadlineNews
        }
        viewModel.dataHeadlineNews.filterNotNull().onEach {
            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val articleList = it.data?.articles?.map { data ->
                        DataArticle(
                            data.author?: "Author",
                            data.publishedAt,
                            data.source.name,
                            data.title,
                            data.url,
                            data.urlToImage
                        )
                    }
                    adapterHeadlineNews.submitData(articleList as ArrayList<DataArticle>)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun observerDataAllNews() {
        val adapterAllNews = VerticalListAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it.url
                )
            )
        }
        binding.rvAllNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterAllNews
        }
        viewModel.dataAllNews.filterNotNull().onEach {
            when (it) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val articleList = it.data?.articles?.map { data ->
                        DataArticle(
                            data.author?: "Author",
                            data.publishedAt,
                            data.source.name,
                            data.title,
                            data.url,
                            data.urlToImage
                        )
                    }
                    adapterAllNews.submitData(articleList as ArrayList<DataArticle>)
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}