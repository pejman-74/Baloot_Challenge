package com.baloot.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.baloot.DEFAULT_QUERY
import com.baloot.R
import com.baloot.data.pagination.TokenExpireException
import com.baloot.databinding.FragmentArticleBinding
import com.baloot.ui.adapter.ArticleAdapter
import com.baloot.util.viewBinding
import com.baloot.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleFragment : Fragment(R.layout.fragment_article), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private val vBinding by viewBinding(FragmentArticleBinding::bind)
    private val vModels: MainViewModel by activityViewModels()
    private val articleAdapter by lazy { ArticleAdapter() }
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        setListeners()

        vBinding.rcArticle.adapter = articleAdapter

        search(DEFAULT_QUERY)


    }

    private fun search(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            vModels.paginatedArticle(query).collectLatest { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }
    }

    private fun setListeners() {

        vBinding.btnRetry.setOnClickListener {
            articleAdapter.retry()
        }

        articleAdapter.addLoadStateListener { loadState ->
            vBinding.ivNoItem.isVisible =
                (loadState.refresh is LoadState.NotLoading || loadState.refresh is LoadState.Error
                        || loadState.refresh is LoadState.Loading) && articleAdapter.itemCount == 0

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
            Log.e("TAG", loadState.toString())
            vBinding.clError.isVisible = errorState != null

            //check token expired
            val refreshErrorState = loadState.refresh as? LoadState.Error
            if (refreshErrorState?.error is TokenExpireException)
                Toast.makeText(requireContext(), "Token expired", Toast.LENGTH_LONG).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { search(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onClose(): Boolean {
        search(DEFAULT_QUERY)
        return false
    }
}