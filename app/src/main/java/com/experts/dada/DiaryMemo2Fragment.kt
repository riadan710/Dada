package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentDiaryMemo2Binding

class DiaryMemo2Fragment : Fragment() {
    lateinit var binding: FragmentDiaryMemo2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMemo2Binding.inflate(inflater, container,false)

        return binding.root
    }
}