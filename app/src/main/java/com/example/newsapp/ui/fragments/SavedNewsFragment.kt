package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.views.MainActivity

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    val viewModel = (activity as MainActivity).newsViewModel

    init {
        viewModel.getSavedNews
    }

    lateinit var binding: FragmentSavedNewsBinding
    lateinit var adapter:NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(inflater,container,false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = NewsAdapter(object :NewsAdapter.OnClickListened{
            override fun itemClicked(position: Int) {
                var article = adapter.differ.currentList[position]
                val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleNewsFragment(article)
                view.findNavController().navigate(action)
            }
        })

        binding.rvSavedNews.adapter = adapter
        binding.rvSavedNews.layoutManager = LinearLayoutManager(requireContext())

viewModel.getSavedNews.observe(requireActivity(), Observer {articles->
    adapter.differ.submitList(articles)
})

    }


}
