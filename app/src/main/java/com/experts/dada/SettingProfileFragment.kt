package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentSettingProfileBinding

class SettingProfileFragment : Fragment() {

    lateinit var binding: FragmentSettingProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingProfileBinding.inflate(inflater, container, false)

        binding.profileCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}