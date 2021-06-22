package com.baloot.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baloot.databinding.FragmentDetailArticleBinding

class DetailArticleFragment : Fragment() {

    private var _vBinding: FragmentDetailArticleBinding? = null
    private val vBinding get() = _vBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vBinding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return vBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vBinding = null
    }

}