package com.baloot.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.baloot.R
import com.baloot.databinding.FragmentProfileBinding
import com.baloot.util.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val vBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vBinding.btnAbout.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAboutMeBottomSheetDialogFragment())
        }
    }

}