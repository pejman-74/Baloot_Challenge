package com.baloot.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.baloot.R
import com.baloot.data.model.Article
import com.baloot.databinding.FragmentDetailArticleBinding
import com.baloot.util.Util.toTime
import com.baloot.util.viewBinding

class DetailArticleFragment : Fragment(R.layout.fragment_detail_article) {

    private val vBinding by viewBinding (FragmentDetailArticleBinding::bind)
    private val args: DetailArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArticleToUi(args.article)
    }

    private fun setArticleToUi(article: Article) {
        with(article) {
            vBinding.ivArticle.load(urlToImage)
            vBinding.tvLink.text = url
            vBinding.tvAuthor.text = author
            vBinding.tvTitle.text = title
            vBinding.tvPublish.text = publishedAt?.toTime()
            vBinding.tvDescription.text = description
            vBinding.tvContent.text = content
        }
    }

}

