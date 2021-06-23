package com.baloot.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.baloot.data.model.Article

class ArticleDiffCallBack : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}