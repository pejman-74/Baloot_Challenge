package com.baloot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.baloot.data.model.Article
import com.baloot.databinding.ArticleItemBinding
import com.baloot.ui.fragment.ArticleFragmentDirections

class ArticleViewHolder(private val articleItemBinding: ArticleItemBinding) :
    RecyclerView.ViewHolder(articleItemBinding.root) {
    fun bind(article: Article) {
        articleItemBinding.ivArticle.load(article.urlToImage)
        articleItemBinding.tvTitle.text = article.title
        articleItemBinding.tvDescription.text = article.description
        articleItemBinding.root.setOnClickListener {
            it.findNavController().navigate(
                ArticleFragmentDirections.actionArticleFragmentToDetailArticleFragment(article)
            )
        }
    }

    companion object {
        fun from(viewGroup: ViewGroup): ArticleViewHolder {
            val context = viewGroup.context
            val layoutInflater = LayoutInflater.from(context)
            val articleItemBinding =
                ArticleItemBinding.inflate(layoutInflater, viewGroup, false)
            return ArticleViewHolder(articleItemBinding)
        }
    }
}