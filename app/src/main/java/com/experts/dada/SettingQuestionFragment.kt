package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentSettingProfileBinding
import com.experts.dada.databinding.FragmentSettingQuestionBinding

class SettingQuestionFragment : Fragment() {
    lateinit var binding: FragmentSettingQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingQuestionBinding.inflate(inflater, container, false)

        binding.questionCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}