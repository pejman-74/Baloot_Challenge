package com.baloot.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.baloot.data.model.Article

class ArticleAdapter : PagingDataAdapter<Article, ArticleViewHolder>(ArticleDiffCallBack()) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }
}

