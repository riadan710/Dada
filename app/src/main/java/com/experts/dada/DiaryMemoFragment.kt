package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentDiaryMemoBinding

class DiaryMemoFragment : Fragment() {

    lateinit var binding: FragmentDiaryMemoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMemoBinding.inflate(inflater, container, false)
        initViewPager()

        val year = arguments?.getInt("year")
        val month = arguments?.getInt("month")?.let { it + 1 }
        val dayOfMonth = arguments?.getInt("dayOfMonth")

        binding.memoDateTv.text = String.format("%04d년 %02d월 %02d일", year, month, dayOfMonth)


        binding.memoCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.memoNotStarIv.setOnClickListener {
            binding.memoNotStarIv.visibility = View.GONE
            binding.memoStarIv.visibility = View.VISIBLE
        }
        binding.memoStarIv.setOnClickListener {
            binding.memoNotStarIv.visibility = View.VISIBLE
            binding.memoStarIv.visibility = View.GONE
        }

        return binding.root
    }

    private fun initViewPager() {
        val viewPagerAdapter = DiaryMemoViewPagerAdapter(requireActivity())
        binding.memoVp.adapter = viewPagerAdapter
    }
}