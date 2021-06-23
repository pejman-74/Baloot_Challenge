package com.baloot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baloot.R
import com.baloot.databinding.FragmentAboutMeBottomSheetDialogBinding
import com.baloot.util.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AboutMeBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val vBinding by viewBinding(FragmentAboutMeBottomSheetDialogBinding::inflate)
    override fun getTheme() = R.style.CustomBottomSheetDialogStyle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return vBinding.root
    }
}