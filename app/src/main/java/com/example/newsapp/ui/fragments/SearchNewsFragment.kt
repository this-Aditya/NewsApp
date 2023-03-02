package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.views.MainActivity
import com.example.newsapp.utils.Constants.Companion.DELAY_WHILE_SEARCHING_NEWS
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SerachNews"

class SearchNewsFragment : Fragment() {
    lateinit var binding: FragmentSearchNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).newsViewModel


        adapter = NewsAdapter(object : NewsAdapter.OnClickListened {
            override fun itemClicked(position: Int) {
                val article = adapter.differ.currentList[position]
                val action =
                    SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleNewsFragment(article)
                findNavController().navigate(action)
            }
        })
        binding.rvSearchNews.adapter = adapter
        binding.rvSearchNews.layoutManager = LinearLayoutManager(requireContext())


        var job: Job? = null

        binding.svNewsSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                job?.cancel()
                MainScope().launch {
                    delay(DELAY_WHILE_SEARCHING_NEWS)
                    val query = p0.toString()
                    if (query.isNotBlank()){
                        viewModel.searchNews(query)

                    }
                }}


        })

        viewModel.searchNewsResponse.observe(requireActivity(), Observer { resource ->
            when (resource) {
                is Resource.Sucess -> {
                    hideProgressBar()
                    resource.data?.let {
                        adapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Failure -> {
                    Log.i(TAG, "Failed Search Query: ${resource.message}")
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showProgressBar() {
        binding.pbSearchNews.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbSearchNews.visibility = View.INVISIBLE
    }

}
