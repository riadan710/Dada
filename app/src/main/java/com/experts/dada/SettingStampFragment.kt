package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentSettingStampBinding

class SettingStampFragment : Fragment() {
    lateinit var binding: FragmentSettingStampBinding
    private lateinit var adapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingStampBinding.inflate(inflater, container, false)

        adapter = StoreAdapter(requireContext())
        binding.stampContentGv.adapter = adapter

        binding.stampCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}