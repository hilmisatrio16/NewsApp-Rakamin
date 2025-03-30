package com.rakamin.newsapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rakamin.newsapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val webSettings = binding.wvNews.settings
        webSettings.javaScriptEnabled = true
        with(binding.wvNews) {
            webViewClient = WebViewClient()
            loadUrl(args.url)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}