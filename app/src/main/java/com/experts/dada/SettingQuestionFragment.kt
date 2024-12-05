package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.questionSendTv.setOnClickListener {
            if(binding.questionContentEt.text.isBlank()) {
                Toast.makeText(context, "문의 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(context, "문의가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}